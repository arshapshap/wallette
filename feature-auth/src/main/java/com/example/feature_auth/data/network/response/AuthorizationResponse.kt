package com.example.feature_auth.data.network.response
import com.google.gson.annotations.SerializedName


data class AuthorizationResponse(
    @SerializedName("isSuccessful")
    val isSuccessful: Boolean?,
    @SerializedName("errorMessage")
    val errorMessage: String?,
    @SerializedName("token")
    val token: String?
)