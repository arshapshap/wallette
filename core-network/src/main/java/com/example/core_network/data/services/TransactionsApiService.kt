package com.example.core_network.data.services

import com.example.core_network.data.models.request.transaction.TransactionCreatingModel
import com.example.core_network.data.models.request.transaction.TransactionEditingModel
import com.example.core_network.data.models.response.BasicResponse
import com.example.core_network.data.models.response.EntityCreatingResponse
import com.example.core_network.data.models.response.TransactionResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface TransactionsApiService {

    @GET("transactions/get")
    suspend fun getTransactions(): ArrayList<TransactionResponse>

    @GET("transactions/get/:id")
    suspend fun getTransactionById(id: Long): TransactionResponse

    @POST("transactions/create")
    suspend fun createTransaction(model: TransactionCreatingModel): EntityCreatingResponse

    @PUT("transactions/update")
    suspend fun updateTransaction(model: TransactionEditingModel): BasicResponse

    @DELETE("transactions/delete/:id")
    suspend fun deleteTransactionById(id: Long): BasicResponse
}