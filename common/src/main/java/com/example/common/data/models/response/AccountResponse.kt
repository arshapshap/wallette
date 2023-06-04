package com.example.common.data.models.response

import com.google.gson.annotations.SerializedName

data class AccountResponse(
    @SerializedName("id")
    val id: Long?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("icon")
    val icon: String?,
    @SerializedName("current_balance")
    val currentBalance: Double?,
    @SerializedName("start_balance")
    val startBalance: Double?
)