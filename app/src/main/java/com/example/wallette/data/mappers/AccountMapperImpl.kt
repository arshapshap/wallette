package com.example.wallette.data.mappers

import com.example.common.data.mappers.AccountMapper
import com.example.common.data.models.response.AccountResponse
import com.example.common.domain.models.Account
import com.example.common.domain.models.Currency
import javax.inject.Inject

class AccountMapperImpl @Inject constructor() : AccountMapper {

    override fun map(response: AccountResponse?): Account {
        return response?.let {
            Account(
                id = response.id ?: "",
                name = response.name ?: "",
                icon = response.icon ?: "",
                balance = response.balance ?: 0,
                currency = Currency.RUB // TODO: получать валюту с бэка
            )
        } ?: Account(
            id = "",
            name = "",
            icon = "",
            balance = 0,
            currency = Currency.RUB
        )
    }
}