package com.example.common.domain.models

import java.io.Serializable

data class Category(
    val id: Long,
    val name: String,
    val icon: CategoryIcon,
    val type: TransactionType
) : Serializable
