package com.example.core_network.data.services

import com.example.core_network.data.NetworkConstants
import com.example.core_network.data.models.request.LoginRequestModel
import com.example.core_network.data.models.request.RegisterRequestModel
import com.example.core_network.data.models.response.AuthorizationResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthorizationApiService {

    @Headers(NetworkConstants.NO_TOKEN_HEADER)
    @POST("auth/authorize")
    suspend fun authorize(@Body model: LoginRequestModel): AuthorizationResponse

    @Headers(NetworkConstants.NO_TOKEN_HEADER)
    @POST("auth/register")
    suspend fun register(@Body model: RegisterRequestModel): AuthorizationResponse
}