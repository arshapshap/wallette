package com.example.feature_settings.presentation.screen.singleAccount

import com.example.common.domain.models.enums.AccountIcon
import com.example.common.domain.models.enums.Currency

data class EditingAccount(
    val id: Long = 0,
    val name: String = "",
    val icon: AccountIcon = AccountIcon.Empty,
    val startBalance: Double? = null,
    val currency: Currency = Currency.RUB // TODO: хранить дефолтную валюту?
)