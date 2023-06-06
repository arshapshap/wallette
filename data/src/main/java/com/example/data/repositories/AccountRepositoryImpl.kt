package com.example.data.repositories

import com.example.common.data.TokenManager
import com.example.common.domain.models.Account
import com.example.common.domain.repositories.AccountRepository
import com.example.core_db.dao.AccountDao
import com.example.data.managers.SyncQueueManager
import com.example.data.managers.enums.RequestType
import com.example.data.mappers.AccountMapper
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val localSource: AccountDao,
    private val tokenManager: TokenManager,
    private val syncQueueManager: SyncQueueManager,
    private val mapper: AccountMapper
): AccountRepository {

    override suspend fun createAccount(account: Account) {
        val local = mapper.map(account)
        val id = localSource.addAccount(local)

        if (!tokenManager.isAuthorized()) return

        syncQueueManager.addRequest(RequestType.Create, account.copy(id = id))
        syncQueueManager.trySynchronize()
    }

    override suspend fun updateAccount(account: Account) {
        val local = mapper.map(account)
        localSource.updateAccount(local)

        if (!tokenManager.isAuthorized()) return

        syncQueueManager.addRequest(RequestType.Update, account)
        syncQueueManager.trySynchronize()
    }

    override suspend fun deleteAccount(account: Account) {
        val local = mapper.map(account)
        localSource.deleteAccount(local)

        if (!tokenManager.isAuthorized()) return

        syncQueueManager.addRequest(RequestType.Delete, account)
        syncQueueManager.trySynchronize()
    }

    override suspend fun getAccounts(): List<Account> {
        val list = localSource.getAccounts()
        return list.map { mapper.map(it) }
    }
}