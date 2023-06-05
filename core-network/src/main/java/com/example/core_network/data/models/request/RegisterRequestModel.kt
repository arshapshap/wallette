package com.example.core_network.data.models.request

import com.google.gson.annotations.SerializedName

data class RegisterRequestModel(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)
