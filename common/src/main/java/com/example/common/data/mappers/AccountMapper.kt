package com.example.common.data.mappers

import com.example.common.data.models.response.AccountResponse
import com.example.common.domain.models.Account

interface AccountMapper {

    fun map(response: AccountResponse?): Account
}