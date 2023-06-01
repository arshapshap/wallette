package com.example.feature_statistics_impl.data.network

import com.example.common.data.models.response.TransactionListResponse
import retrofit2.http.GET

interface TransactionsApiService {

    @GET("transactions")
    suspend fun getTransactions(): TransactionListResponse
}