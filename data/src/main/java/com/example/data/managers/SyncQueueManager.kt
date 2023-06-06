package com.example.data.managers

import com.example.common.domain.models.Account
import com.example.common.domain.models.Category
import com.example.common.domain.models.Tag
import com.example.common.domain.models.Transaction
import com.example.core_db.dao.SyncRequestDao
import com.example.data.managers.enums.EntityType
import com.example.data.managers.enums.RequestType
import com.example.data.managers.interfaces.*
import com.example.data.mappers.SyncRequestMapper
import javax.inject.Inject

class SyncQueueManager @Inject constructor(
    private val localSource: SyncRequestDao,
    private val mapper: SyncRequestMapper,
    private val accountRepository: AccountRemoteRepository,
    private val categoryRepository: CategoryRemoteRepository,
    private val tagRepository: TagRemoteRepository,
    private val transactionRepository: TransactionRemoteRepository
) {

    suspend fun addRequest(requestType: RequestType, entity: Any) {
        val request = mapper.map(requestType, entity)
        localSource.addRequest(request)
    }

    suspend fun trySynchronize() {
        val requests = localSource.getRequests()
        for (request in requests) {
            try {
                val isSuccessful = when (mapper.getEntityType(request)) {
                    EntityType.Account ->  {
                        val pair = mapper.mapToAccountRequest(request)
                        request(pair.first, pair.second)
                    }
                    EntityType.Category -> {
                        val pair = mapper.mapToCategoryRequest(request)
                        request(pair.first, pair.second)
                    }
                    EntityType.Tag -> {
                        val pair = mapper.mapToTagRequest(request)
                        request(pair.first, pair.second)
                    }
                    EntityType.Transaction -> {
                        val pair = mapper.mapToTransactionRequest(request)
                        request(pair.first, pair.second)
                    }
                }
                if (!isSuccessful) return
            } catch (error: Exception) {
                return
            }

            localSource.deleteRequest(request)
        }
    }

    private suspend fun request(type: RequestType, account: Account): Boolean {
        val result = when (type) {
            RequestType.Create -> accountRepository.createAccountRemote(account)
            RequestType.Update -> accountRepository.updateAccountRemote(account)
            RequestType.Delete -> accountRepository.deleteAccountRemote(account)
        }
        return result.isSuccessful
    }

    private suspend fun request(type: RequestType, category: Category): Boolean {
        val result = when (type) {
            RequestType.Create -> categoryRepository.createCategoryRemote(category)
            RequestType.Update -> categoryRepository.updateCategoryRemote(category)
            RequestType.Delete -> categoryRepository.deleteCategoryRemote(category)
        }
        return result.isSuccessful
    }

    private suspend fun request(type: RequestType, tag: Tag): Boolean {
        val result = when (type) {
            RequestType.Create -> tagRepository.createTagRemote(tag)
            RequestType.Update -> tagRepository.updateTagRemote(tag)
            RequestType.Delete -> tagRepository.deleteTagRemote(tag)
        }
        return result.isSuccessful
    }

    private suspend fun request(type: RequestType, transaction: Transaction): Boolean {
        val result = when (type) {
            RequestType.Create -> transactionRepository.createTransactionRemote(transaction)
            RequestType.Update -> transactionRepository.updateTransactionRemote(transaction)
            RequestType.Delete -> transactionRepository.deleteTransactionRemote(transaction)
        }
        return result.isSuccessful
    }
}