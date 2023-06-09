package com.example.core_network.data.models.request.tag

import com.google.gson.annotations.SerializedName

data class TagCreatingModel(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("color")
    val color: String,
)
