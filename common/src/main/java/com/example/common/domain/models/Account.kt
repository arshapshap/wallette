package com.example.common.domain.models

import com.example.common.domain.models.enums.AccountIcon
import com.example.common.domain.models.enums.Currency
import java.io.Serializable

data class Account(
    val id: Long,
    val name: String,
    val icon: AccountIcon,
    val currentBalance: Double,
    val startBalance: Double,
    val currency: Currency
) : Serializable
