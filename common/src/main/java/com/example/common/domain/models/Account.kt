package com.example.common.domain.models

data class Account(
    val id: String,
    val name: String,
    val icon: String,
    val balance: Int,
    val currency: Currency
)