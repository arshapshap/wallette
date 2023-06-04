package com.example.data.repositories

import com.example.common.domain.models.Account
import com.example.common.domain.models.AccountIcon
import com.example.common.domain.models.Currency
import com.example.common.domain.repositories.AccountRepository
import com.example.core_db.dao.AccountDao
import com.example.data.mappers.AccountMapper
import javax.inject.Inject
import kotlin.random.Random

class AccountRepositoryImpl @Inject constructor(
    private val localSource: AccountDao,
    private val mapper: AccountMapper
): AccountRepository {

    override suspend fun createAccount(account: Account) {
        val local = mapper.map(account)
        localSource.addAccount(local)
    }

    override suspend fun editAccount(account: Account) {
        val local = mapper.map(account)
        localSource.updateAccount(local)
    }

    override suspend fun deleteAccount(account: Account) {
        val local = mapper.map(account)
        localSource.deleteAccount(local)
    }

    override suspend fun getAccounts(): List<Account> {
        val list = localSource.getAccounts()
        return list.map { mapper.map(it) }
    }

    private fun getRandomAccounts(): List<Account> {
        val list = arrayListOf<Account>()
        val rand = Random(1234)
        for (i in 0..10) {
            list.add(
                Account(
                    id = i.toLong(),
                    name = "Счёт $i",
                    icon = AccountIcon.values().filter { it.name != "Empty" }.random(rand),
                    currentBalance = rand.nextDouble(5000.0),
                    startBalance = 0.0,
                    currency = Currency.RUB
                )
            )
        }
        return list
    }
}