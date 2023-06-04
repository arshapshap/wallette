package com.example.data.repositories

import com.example.common.domain.models.Account
import com.example.common.domain.repositories.AccountRepository
import com.example.core_db.dao.AccountDao
import com.example.data.mappers.AccountMapper
import javax.inject.Inject

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
}