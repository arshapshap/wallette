package com.example.core_network.data.models.request.tag

import com.google.gson.annotations.SerializedName

data class TagEditingModel(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("color")
    val color: String,
)
