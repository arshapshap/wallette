package com.example.core_network.data.models.request.category

import com.google.gson.annotations.SerializedName

data class CategoryEditingModel(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("icon")
    val icon: String
)
