package com.example.core_network.data.models.request.category

import com.google.gson.annotations.SerializedName

data class CategoryCreatingModel(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("transactionType")
    val type: String
)
