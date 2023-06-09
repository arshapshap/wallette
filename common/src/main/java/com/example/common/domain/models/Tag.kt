package com.example.common.domain.models

import java.io.Serializable

data class Tag(
    val id: Long,
    val name: String,
    val color: String,
) : Serializable
