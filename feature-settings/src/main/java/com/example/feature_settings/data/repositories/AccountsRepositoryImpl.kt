package com.example.feature_settings.data.repositories

import com.example.common.domain.models.Account
import com.example.common.domain.models.Currency
import com.example.feature_settings.domain.repositories.AccountsRepository
import javax.inject.Inject

class AccountsRepositoryImpl @Inject constructor(): AccountsRepository {

    override suspend fun getAccounts(): List<Account> {
        val account = Account(
            id = "acc123",
            name = "Main",
            icon = "card",
            balance = 3500,
            currency = Currency.RUB
        )
        return listOf(account, account, account, account, account, account, account, account, account, account, account, account, account)
    }
}