package com.example.data.mappers

import com.example.common.domain.models.Account
import com.example.common.domain.models.Category
import com.example.common.domain.models.Tag
import com.example.common.domain.models.Transaction
import com.example.core_db.models.SyncRequest
import com.example.data.managers.enums.EntityType
import com.example.data.managers.enums.RequestType
import com.google.gson.Gson
import javax.inject.Inject

class SyncRequestMapper @Inject constructor(
    private val gson: Gson
) {

    fun map(requestType: RequestType, entity: Any): SyncRequest {
        return when (entity) {
            is Account -> map(requestType, entity)
            is Category -> map(requestType, entity)
            is Tag -> map(requestType, entity)
            is Transaction -> map(requestType, entity)
            else -> throw IllegalArgumentException("Entity must be one of domain models (Account, Category, Tag, Transaction)")
        }
    }

    fun mapToAccountRequest(request: SyncRequest): Pair<RequestType, Account> = Pair(
        RequestType.valueOf(request.requestType),
        gson.fromJson(request.entityJson, Account::class.java)
    )

    fun mapToCategoryRequest(request: SyncRequest): Pair<RequestType, Category> = Pair(
        RequestType.valueOf(request.requestType),
        gson.fromJson(request.entityJson, Category::class.java)
    )

    fun mapToTagRequest(request: SyncRequest): Pair<RequestType, Tag> = Pair(
        RequestType.valueOf(request.requestType),
        gson.fromJson(request.entityJson, Tag::class.java)
    )

    fun mapToTransactionRequest(request: SyncRequest): Pair<RequestType, Transaction> = Pair(
        RequestType.valueOf(request.requestType),
        gson.fromJson(request.entityJson, Transaction::class.java)
    )

    fun getEntityType(request: SyncRequest): EntityType = EntityType.valueOf(request.entityType)

    private fun map(requestType: RequestType, account: Account): SyncRequest = SyncRequest(
        id = 0,
        requestType = requestType.name,
        entityType = EntityType.Account.name,
        entityId = account.id,
        entityJson = gson.toJson(account)
    )

    private fun map(requestType: RequestType, category: Category): SyncRequest = SyncRequest(
        id = 0,
        requestType = requestType.name,
        entityType = EntityType.Category.name,
        entityId = category.id,
        entityJson = gson.toJson(category)
    )

    private fun map(requestType: RequestType, tag: Tag): SyncRequest = SyncRequest(
        id = 0,
        requestType = requestType.name,
        entityType = EntityType.Tag.name,
        entityId = tag.id,
        entityJson = gson.toJson(tag)
    )

    private fun map(requestType: RequestType, transaction: Transaction): SyncRequest = SyncRequest(
        id = 0,
        requestType = requestType.name,
        entityType = EntityType.Transaction.name,
        entityId = transaction.id,
        entityJson = gson.toJson(transaction)
    )
}