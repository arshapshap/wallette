package com.example.common.domain.models

import java.io.Serializable

data class Category(
    val id: String,
    val name: String,
    val icon: CategoryIcon,
    val type: TransactionType
) : Serializable
