package com.example.common.domain.models

data class Category(
    val id: String,
    val name: String,
    val icon: CategoryIcon,
    val type: TransactionType
)
