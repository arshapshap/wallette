package com.example.core_network.data.models.request.transaction

import com.google.gson.annotations.SerializedName
import java.util.*

data class TransactionCreatingModel(
    @SerializedName("id")
    val id: Long,
    @SerializedName("type")
    val type: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("description")
    val description: String,
    @SerializedName("accountId")
    val accountId: Long,
    @SerializedName("accountDestinationId")
    val accountDestinationId: Long?,
    @SerializedName("categoryId")
    val categoryId: Long?,
    @SerializedName("tagsId")
    val tagsId: List<Long>
)