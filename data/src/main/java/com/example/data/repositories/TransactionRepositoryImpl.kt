package com.example.data.repositories

import com.example.common.data.TokenManager
import com.example.common.domain.models.Tag
import com.example.common.domain.models.Transaction
import com.example.common.domain.models.network.BasicResult
import com.example.common.domain.repositories.TransactionRepository
import com.example.core_db.dao.TransactionDao
import com.example.core_db.models.TransactionTagCrossRef
import com.example.core_network.data.services.TransactionsApiService
import com.example.data.mappers.BasicResultMapper
import com.example.data.mappers.TransactionMapper
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val localSource: TransactionDao,
    private val remoteSource: TransactionsApiService,
    private val tokenManager: TokenManager,
    private val mapper: TransactionMapper,
    private val resultMapper: BasicResultMapper
) : TransactionRepository {

    override suspend fun createTransaction(transaction: Transaction) {
        val local = mapper.map(transaction)
        val id = localSource.addTransaction(local)
        editTags(id, transaction.tags)

        if (!tokenManager.isAuthorized()) return

        createTransactionRemote(transaction.copy(id = id))
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        val local = mapper.map(transaction)
        editTags(local.transactionId, transaction.tags)
        localSource.updateTransaction(local)

        if (!tokenManager.isAuthorized()) return

        updateTransactionRemote(transaction)
    }

    override suspend fun deleteTransaction(transaction: Transaction) {
        val local = mapper.map(transaction)
        localSource.deleteTransaction(local)

        if (!tokenManager.isAuthorized()) return

        deleteTransactionRemote(transaction.id)
    }

    override suspend fun getTransactions(): List<Transaction> {
        val list = localSource.getTransactions()
        return list.map { mapper.map(it) }
    }

    private suspend fun createTransactionRemote(transaction: Transaction): BasicResult {
        val model = mapper.mapToCreatingModel(transaction)
        val response = remoteSource.createTransaction(model)
        return resultMapper.map(response)
    }

    private suspend fun updateTransactionRemote(transaction: Transaction): BasicResult {
        val model = mapper.mapToEditingModel(transaction)
        val response = remoteSource.updateTransaction(model)
        return resultMapper.map(response)
    }

    private suspend fun deleteTransactionRemote(id: Long): BasicResult {
        val response = remoteSource.deleteTransactionById(id)
        return resultMapper.map(response)
    }

    private suspend fun editTags(transactionId: Long, newTags: List<Tag>) {
        val currentTags = localSource.getTags(transactionId)
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