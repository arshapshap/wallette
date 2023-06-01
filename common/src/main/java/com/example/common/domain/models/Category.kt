package com.example.common.domain.models

data class Category(
    val id: String,
    val name: String,
    val icon: String,
    val type: TransactionType
)
