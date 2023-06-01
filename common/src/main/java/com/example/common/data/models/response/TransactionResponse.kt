package com.example.common.data.models.response

import com.google.gson.annotations.SerializedName

data class TransactionResponse(
    @SerializedName("id")
    val id: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("date")
    val date: String?,
    @SerializedName("amount")
    val amount: Int?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("account")
    val account: AccountResponse?,
    @SerializedName("category")
    val category: CategoryResponse?,
    @SerializedName("transactionGroup")
    val transactionGroup: TransactionResponse?,
    @SerializedName("isTransactionGroup")
    val isTransactionGroup: Boolean?,
    @SerializedName("tags")
    val tags: List<TagResponse?>?
)