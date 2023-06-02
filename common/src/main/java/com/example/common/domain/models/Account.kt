package com.example.common.domain.models

data class Account(
    val id: String,
    val name: String,
    val icon: AccountIcon,
    val balance: Int,
    val currency: Currency
)
