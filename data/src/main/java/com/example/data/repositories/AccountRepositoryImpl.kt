package com.example.data.repositories

import com.example.common.data.TokenManager
import com.example.common.domain.models.Account
import com.example.common.domain.models.network.BasicResult
import com.example.common.domain.repositories.AccountRepository
import com.example.core_db.dao.AccountDao
import com.example.core_network.data.services.AccountsApiService
import com.example.data.mappers.AccountMapper
import com.example.data.mappers.BasicResultMapper
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val localSource: AccountDao,
    private val remoteSource: AccountsApiService,
    private val tokenManager: TokenManager,
    private val mapper: AccountMapper,
    private val resultMapper: BasicResultMapper
): AccountRepository {

    override suspend fun createAccount(account: Account) {
        val local = mapper.map(account)
        val id = localSource.addAccount(local)

        if (!tokenManager.isAuthorized()) return

        val result = createAccountRemote(account.copy(id = id))
        if (result.isSuccessful)
            setSynchronized(account)
    }

    override suspend fun updateAccount(account: Account) {
        val local = mapper.map(account)
        localSource.updateAccount(local)

        if (!tokenManager.isAuthorized()) return

        val result = updateAccountRemote(account)
        if (result.isSuccessful)
            setSynchronized(account)
    }

    override suspend fun deleteAccount(account: Account) {
        val local = mapper.map(account)
        setMustBeDeleted(account)

        if (!tokenManager.isAuthorized()) {
            localSource.deleteAccount(local)
            return
        }

        val result = deleteAccountRemote(account.id)
        if (result.isSuccessful)
            localSource.deleteAccount(local)
    }

    override suspend fun getAccounts(): List<Account> {
        val list = localSource.getAccounts()
        return list.map { mapper.map(it) }
    }

    private suspend fun createAccountRemote(account: Account): BasicResult {
        val model = mapper.mapToCreatingModel(account)
        val response = remoteSource.createAccount(model)
        return resultMapper.map(response)
    }

    private suspend fun updateAccountRemote(account: Account): BasicResult {
        val model = mapper.mapToEditingModel(account)
        val response = remoteSource.updateAccount(model)
        return resultMapper.map(response)
    }

    private suspend fun deleteAccountRemote(id: Long): BasicResult {
        val response = remoteSource.deleteAccountById(id)
        return resultMapper.map(response)
    }

    private suspend fun setSynchronized(account: Account) {
        val local = mapper.map(account)
        localSource.updateAccount(
            local.copy(isSynchronized = true)
        )
    }

    private suspend fun setMustBeDeleted(account: Account) {
        val local = mapper.map(account)
        localSource.updateAccount(
            local.copy(mustBeDeleted = true)
        )
    }
}