package com.example.data.repositories.remote

import com.example.common.domain.models.Transaction
import com.example.common.domain.models.network.BasicResult
import com.example.core_network.data.services.TransactionsApiService
import com.example.data.managers.interfaces.TransactionRemoteRepository
import com.example.data.mappers.BasicResultMapper
import com.example.data.mappers.TransactionMapper
import javax.inject.Inject

class TransactionRemoteRepositoryImpl @Inject constructor(
    private val remoteSource: TransactionsApiService,
    private val mapper: TransactionMapper,
    private val resultMapper: BasicResultMapper
) : TransactionRemoteRepository {

    override suspend fun createTransactionRemote(transaction: Transaction): BasicResult {
        val model = mapper.mapToCreatingModel(transaction)
        val response = remoteSource.createTransaction(model)
        return resultMapper.map(response)
    }

    override suspend fun updateTransactionRemote(transaction: Transaction): BasicResult {
        val model = mapper.mapToEditingModel(transaction)
        val response = remoteSource.updateTransaction(model)
        return resultMapper.map(response)
    }

    override suspend fun deleteTransactionRemote(transaction: Transaction): BasicResult {
        val response = remoteSource.deleteTransactionById(transaction.id)
        return resultMapper.map(response)
    }
}