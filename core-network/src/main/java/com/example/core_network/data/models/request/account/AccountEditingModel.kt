package com.example.core_network.data.models.request.account

import com.google.gson.annotations.SerializedName

data class AccountEditingModel(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("iconUrl")
    val icon: String,
    @SerializedName("currentBalance")
    val currentBalance: Double,
    @SerializedName("startBalance")
    val startBalance: Double,
    @SerializedName("currency")
    val currency: String,
)