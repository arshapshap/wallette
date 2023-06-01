package com.example.common.data.models.response

import com.google.gson.annotations.SerializedName

data class AccountResponse(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("icon")
    val icon: String?,
    @SerializedName("balance")
    val balance: Int?
)