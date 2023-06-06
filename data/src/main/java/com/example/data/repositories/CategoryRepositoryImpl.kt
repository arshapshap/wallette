package com.example.data.repositories

import com.example.common.data.TokenManager
import com.example.common.domain.models.Category
import com.example.common.domain.repositories.CategoryRepository
import com.example.core_db.dao.CategoryDao
import com.example.data.managers.SyncQueueManager
import com.example.data.managers.enums.RequestType
import com.example.data.mappers.CategoryMapper
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val localSource: CategoryDao,
    private val tokenManager: TokenManager,
    private val syncQueueManager: SyncQueueManager,
    private val mapper: CategoryMapper
): CategoryRepository {

    override suspend fun createCategory(category: Category) {
        val local = mapper.map(category)
        val id = localSource.addCategory(local)

        if (!tokenManager.isAuthorized()) return

        syncQueueManager.addRequest(RequestType.Create, category.copy(id = id))
        syncQueueManager.trySynchronize()
    }

    override suspend fun updateCategory(category: Category) {
        val local = mapper.map(category)
        localSource.updateCategory(local)

        if (!tokenManager.isAuthorized()) return

        syncQueueManager.addRequest(RequestType.Update, category)
        syncQueueManager.trySynchronize()
    }

    override suspend fun deleteCategory(category: Category) {
        val local = mapper.map(category)
        localSource.deleteCategory(local)

        if (!tokenManager.isAuthorized()) return

        syncQueueManager.addRequest(RequestType.Delete, category)
        syncQueueManager.trySynchronize()
    }

    override suspend fun getCategories(): List<Category> {
        val list = localSource.getCategories()
        return list.map { mapper.map(it) }
    }
}