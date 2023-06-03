package com.example.feature_settings.domain.repositories

import com.example.common.domain.models.Account

interface AccountsRepository {

    suspend fun getAccounts(): List<Account>
}