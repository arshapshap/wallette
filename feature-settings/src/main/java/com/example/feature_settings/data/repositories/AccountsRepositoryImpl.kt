package com.example.feature_settings.data.repositories

import com.example.common.domain.models.Account
import com.example.common.domain.models.AccountIcon
import com.example.common.domain.models.Currency
import com.example.feature_settings.domain.repositories.AccountsRepository
import javax.inject.Inject
import kotlin.random.Random

class AccountsRepositoryImpl @Inject constructor(): AccountsRepository {

    override suspend fun getAccounts(): List<Account> {
        //TODO: УБРАТЬ РАНДОМ
        val list = arrayListOf<Account>()
        val rand = Random(1234)
        for (i in 0..10) {
            list.add(
                Account(
                    id = i.toString(),
                    name = "Счёт $i",
                    icon = AccountIcon.values().filter { it.name != "Empty" }.random(rand),
                    balance = (0..5000).random(rand),
                    currency = Currency.RUB
                )
            )
        }
        return list
    }
}