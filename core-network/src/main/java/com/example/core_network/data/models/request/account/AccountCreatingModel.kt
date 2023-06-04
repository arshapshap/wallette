package com.example.core_network.data.models.request.account

import com.google.gson.annotations.SerializedName

data class AccountCreatingModel(
    @SerializedName("id")
    val id: Long?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("iconUrl")
    val icon: String?,
    @SerializedName("startBalance")
    val startBalance: Double?,
    @SerializedName("currency")
    val currency: String?,
)