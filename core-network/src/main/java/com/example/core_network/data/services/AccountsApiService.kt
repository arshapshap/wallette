package com.example.core_network.data.services

import com.example.core_network.data.models.request.account.AccountCreatingModel
import com.example.core_network.data.models.request.account.AccountEditingModel
import com.example.core_network.data.models.response.*
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface AccountsApiService {

    @GET("accounts/get")
    suspend fun getAccountss(): ArrayList<AccountResponse>

    @GET("accounts/get/:id")
    suspend fun getAccountsById(id: Long): AccountResponse

    @POST("accounts/create")
    suspend fun createAccounts(model: AccountCreatingModel): EntityCreatingResponse

    @PUT("accounts/update")
    suspend fun updateAccounts(model: AccountEditingModel): BasicResponse

    @DELETE("accounts/delete/:id")
    suspend fun deleteAccountsById(id: Long): BasicResponse
}