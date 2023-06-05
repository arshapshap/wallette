package com.example.data.repositories

import com.example.common.data.TokenManager
import com.example.common.domain.exceptions.WrongTokenException
import com.example.common.domain.models.Account
import com.example.common.domain.models.network.BasicResult
import com.example.common.domain.repositories.AccountRepository
import com.example.core_db.dao.AccountDao
import com.example.core_network.data.services.AccountsApiService
import com.example.data.mappers.AccountMapper
import com.example.data.mappers.BasicResultMapper
import retrofit2.HttpException
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

        createAccountRemote(account.copy(id = id))
    }

    override suspend fun updateAccount(account: Account) {
        val local = mapper.map(account)
        localSource.updateAccount(local)

        if (!tokenManager.isAuthorized()) return

        updateAccountRemote(account)
    }

    override suspend fun deleteAccount(account: Account) {
        val local = mapper.map(account)
        localSource.deleteAccount(local)

        if (!tokenManager.isAuthorized()) return

        deleteAccountRemote(account.id)
    }

    override suspend fun getAccounts(): List<Account> {
        val list = localSource.getAccounts()
        return list.filter { !it.mustBeDeleted }.map { mapper.map(it) }
    }

    override suspend fun synchronize(account: Account): Boolean {
        val local = localSource.getAccountById(account.id)
        if (local.isSynchronized) return true

        kotlin.runCatching {
            remoteSource.getAccountById(account.id)
            val result = updateAccountRemote(account)
            return result.isSuccessful
        }.onFailure {
            if (it is HttpException && it.code() == 404) {
                val result = createAccountRemote(account)
                return result.isSuccessful
            }
            if (it is HttpException && it.code() == 401) {
                throw WrongTokenException()
            }
            throw it
        }

        return false
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
}