package com.example.core_network.data.services

import com.example.core_network.data.models.request.account.AccountCreatingModel
import com.example.core_network.data.models.request.account.AccountEditingModel
import com.example.core_network.data.models.response.*
import retrofit2.http.*

interface AccountsApiService {

    @GET("account/get")
    suspend fun getAccounts(): ArrayList<AccountResponse>

    @GET("account/get/:id")
    suspend fun getAccountById(@Query("id")  id: Long): AccountResponse

    @POST("account/create")
    suspend fun createAccount(@Body model: AccountCreatingModel): BasicResponse

    @PUT("account/update")
    suspend fun updateAccount(@Body model: AccountEditingModel): BasicResponse

    @DELETE("account/delete/:id")
    suspend fun deleteAccountById(@Query("id") id: Long): BasicResponse
}