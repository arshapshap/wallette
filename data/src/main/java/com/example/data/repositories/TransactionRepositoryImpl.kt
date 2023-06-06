package com.example.data.repositories

import com.example.common.data.TokenManager
import com.example.common.domain.models.Tag
import com.example.common.domain.models.Transaction
import com.example.common.domain.repositories.AccountRepository
import com.example.common.domain.repositories.TransactionRepository
import com.example.core_db.dao.TransactionDao
import com.example.core_db.models.TransactionTagCrossRef
import com.example.data.managers.SyncQueueManager
import com.example.data.managers.enums.RequestType
import com.example.data.mappers.TransactionMapper
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val localSource: TransactionDao,
    private val accountRepository: AccountRepository,
    private val tokenManager: TokenManager,
    private val syncQueueManager: SyncQueueManager,
    private val mapper: TransactionMapper
) : TransactionRepository {

    override suspend fun createTransaction(transaction: Transaction) {
        val local = mapper.map(transaction)
        val id = localSource.addTransaction(local)
        editTags(id, transaction.tags)

        val accountWithUpdatedBalance = transaction.account.copy(
            currentBalance = transaction.account.currentBalance + transaction.amount
        )
        accountRepository.updateAccount(accountWithUpdatedBalance)

        if (!tokenManager.isAuthorized()) return

        syncQueueManager.addRequest(RequestType.Create, transaction.copy(id = id))
        syncQueueManager.addRequest(RequestType.Update, accountWithUpdatedBalance)
        syncQueueManager.trySynchronize()
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        val local = mapper.map(transaction)
        editTags(local.transactionId, transaction.tags)

        val oldTransactionAmount = getTransactionById(transaction.id).amount
        localSource.updateTransaction(local)

        val accountWithUpdatedBalance = transaction.account.copy(
            currentBalance = transaction.account.currentBalance - oldTransactionAmount + transaction.amount
        )
        accountRepository.updateAccount(accountWithUpdatedBalance)

        if (!tokenManager.isAuthorized()) return

        syncQueueManager.addRequest(RequestType.Update, transaction)
        syncQueueManager.addRequest(RequestType.Update, accountWithUpdatedBalance)
        syncQueueManager.trySynchronize()
    }

    override suspend fun deleteTransaction(transaction: Transaction) {
        val local = mapper.map(transaction)
        localSource.deleteTransaction(local)

        val accountWithUpdatedBalance = transaction.account.copy(
            currentBalance = transaction.account.currentBalance - transaction.amount
        )
        accountRepository.updateAccount(accountWithUpdatedBalance)

        if (!tokenManager.isAuthorized()) return

        syncQueueManager.addRequest(RequestType.Delete, transaction)
        syncQueueManager.addRequest(RequestType.Update, accountWithUpdatedBalance)
        syncQueueManager.trySynchronize()
    }

    override suspend fun getTransactions(): List<Transaction> {
        val list = localSource.getTransactions()
        return list.map { mapper.map(it) }
    }

    override suspend fun getTransactionById(id: Long): Transaction {
        val local = localSource.getTransactionById(id)
        return mapper.map(local)
    }

    private suspend fun editTags(transactionId: Long, newTags: List<Tag>) {
        val currentTags = localSource.getTagsByTransactionId(transactionId)
        newTags
            .filter {
                it.id !in currentTags
            }
            .forEach {
                localSource.addTransactionTag(
                    TransactionTagCrossRef(
                        transactionId = transactionId,
                        tagId = it.id
                    )
                )
            }
        currentTags
            .filter { it !in newTags.map { it.id } }
            .forEach {
                localSource.deleteTransactionTag(
                    TransactionTagCrossRef(
                        transactionId = transactionId,
                        tagId = it
                    )
                )
            }
    }
}