package com.example.feature_statistics_impl.data.repositories

import com.example.common.data.mappers.TransactionMapper
import com.example.common.domain.models.Transaction
import com.example.feature_statistics_impl.data.network.TransactionsApiService
import com.example.feature_statistics_impl.domain.repositories.TransactionsRepository
import javax.inject.Inject

class TransactionsRepositoryImpl @Inject constructor(
    private val service: TransactionsApiService,
    private val mapper: TransactionMapper
) : TransactionsRepository {

    override suspend fun getTransactions(): List<Transaction> {
        val result = service.getTransactions()
        return result.map { mapper.map(it) }
    }
}