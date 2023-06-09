package com.example.core_network.data.models.response

import com.google.gson.annotations.SerializedName

data class TagResponse(
    @SerializedName("id")
    val id: Long?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("color")
    val color: String?
)