package com.example.common.domain.models

import java.io.Serializable

data class Account(
    val id: String,
    val name: String,
    val icon: AccountIcon,
    val currentBalance: Double,
    val startBalance: Double,
    val currency: Currency
) : Serializable
