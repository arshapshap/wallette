package com.example.data.repositories

import com.example.common.domain.models.Tag
import com.example.common.domain.models.Transaction
import com.example.common.domain.repositories.TransactionRepository
import com.example.core_db.dao.TransactionDao
import com.example.core_db.models.TransactionTagCrossRef
import com.example.data.mappers.TransactionMapper
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val localSource: TransactionDao,
    private val mapper: TransactionMapper
) : TransactionRepository {

    override suspend fun createTransaction(transaction: Transaction) {
        val local = mapper.map(transaction)
        localSource.addTransaction(local)
    }

    override suspend fun editTransaction(transaction: Transaction) {
        val local = mapper.map(transaction)
        editTags(local.transactionId, transaction.tags)
        localSource.updateTransaction(local)
    }

    override suspend fun deleteTransaction(transaction: Transaction) {
        val local = mapper.map(transaction)
        localSource.deleteTransaction(local)
    }

    override suspend fun getTransactions(): List<Transaction> {
        val list = localSource.getTransactions()
        return list.map { mapper.map(it) }
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
                        tagId = it.id)
                )
            }
        currentTags
            .filter { it !in newTags.map { it.id } }
            .forEach {
                localSource.deleteTransactionTag(
                    TransactionTagCrossRef(
                        transactionId = transactionId,
                        tagId = it)
                )
            }
    }
}