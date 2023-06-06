package com.example.feature_settings.presentation.screen.singleAccount

import com.example.common.domain.models.enums.AccountIcon
import com.example.common.domain.models.enums.Currency

data class EditingAccount(
    val id: Long = 0,
    val name: String = "",
    val icon: AccountIcon = AccountIcon.Empty,
    val startBalance: Double = 0.0,
    val currentBalance: Double = 0.0,
    val currency: Currency = Currency.RUB
)