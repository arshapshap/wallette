package com.example.core_network.data.models.response

import com.google.gson.annotations.SerializedName

data class BasicResponse(
    @SerializedName("isSuccessful")
    val isSuccessful: Boolean?,
    @SerializedName("errorMessage")
    val errorMessage: String?
)