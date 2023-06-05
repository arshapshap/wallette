package com.example.core_network.data.models.response

import com.google.gson.annotations.SerializedName

data class AccountResponse(
    @SerializedName("id")
    val id: Long?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("icon")
    val icon: String?,
    @SerializedName("currentBalance")
    val currentBalance: Double?,
    @SerializedName("startBalance")
    val startBalance: Double?,
    @SerializedName("currency")
    val currency: String?,
)