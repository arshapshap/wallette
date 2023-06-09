package com.example.common.domain.repositories

import com.example.common.domain.models.Transaction

interface TransactionRepository {

    suspend fun createTransaction(transaction: Transaction)

    suspend fun updateTransaction(transaction: Transaction)

    suspend fun deleteTransaction(transaction: Transaction)

    suspend fun getTransactions(): List<Transaction>

    suspend fun getTransactionById(id: Long): Transaction
}