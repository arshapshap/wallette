package com.example.common.domain.repositories

import com.example.common.domain.models.Account

interface AccountRepository {

    suspend fun createAccount(account: Account)

    suspend fun editAccount(account: Account)

    suspend fun deleteAccount(account: Account)

    suspend fun getAccounts(): List<Account>
}