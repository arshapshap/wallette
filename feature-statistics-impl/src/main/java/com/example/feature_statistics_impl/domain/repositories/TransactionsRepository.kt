package com.example.feature_statistics_impl.domain.repositories

import com.example.common.domain.models.Transaction

interface TransactionsRepository {

    suspend fun getTransactions(): List<Transaction>
}