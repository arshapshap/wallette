package com.example.feature_auth.data.network

import com.example.common.data.NetworkConstants
import com.example.feature_auth.data.network.request.LoginRequestModel
import com.example.feature_auth.data.network.request.RegisterRequestModel
import com.example.feature_auth.data.network.response.AuthorizationResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthorizationApiService {

    @Headers(NetworkConstants.NO_TOKEN_HEADER)
    @POST("user/authorize")
    suspend fun authorize(@Body model: LoginRequestModel): AuthorizationResponse

    @Headers(NetworkConstants.NO_TOKEN_HEADER)
    @POST("user/register")
    suspend fun register(@Body model: RegisterRequestModel): AuthorizationResponse
}