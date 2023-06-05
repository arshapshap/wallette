package com.example.core_network.data.services

import com.example.core_network.data.models.request.transaction.TransactionCreatingModel
import com.example.core_network.data.models.request.transaction.TransactionEditingModel
import com.example.core_network.data.models.response.BasicResponse
import com.example.core_network.data.models.response.TransactionResponse
import retrofit2.http.*

interface TransactionsApiService {

    @GET("transactions/get")
    suspend fun getTransactions(): ArrayList<TransactionResponse>

    @GET("transactions/get/:id")
    suspend fun getTransactionById(@Query("id")  id: Long): TransactionResponse

    @POST("transactions/create")
    suspend fun createTransaction(@Body model: TransactionCreatingModel): BasicResponse

    @PUT("transactions/update")
    suspend fun updateTransaction(@Body model: TransactionEditingModel): BasicResponse

    @DELETE("transactions/delete/:id")
    suspend fun deleteTransactionById(@Query("id")  id: Long): BasicResponse
}