package com.example.data.repositories

import com.example.common.data.TokenManager
import com.example.common.domain.models.Category
import com.example.common.domain.models.network.BasicResult
import com.example.common.domain.repositories.CategoryRepository
import com.example.core_db.dao.CategoryDao
import com.example.core_network.data.services.CategoriesApiService
import com.example.data.mappers.BasicResultMapper
import com.example.data.mappers.CategoryMapper
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val localSource: CategoryDao,
    private val remoteSource: CategoriesApiService,
    private val tokenManager: TokenManager,
    private val mapper: CategoryMapper,
    private val resultMapper: BasicResultMapper
): CategoryRepository {

    override suspend fun createCategory(category: Category) {
        val local = mapper.map(category)
        localSource.addCategory(local)
    }

    override suspend fun updateCategory(category: Category) {
        val local = mapper.map(category)
        localSource.updateCategory(local)
    }

    override suspend fun deleteCategory(category: Category) {
        val local = mapper.map(category)
        localSource.deleteCategory(local)
    }

    override suspend fun getCategories(): List<Category> {
        val list = localSource.getCategories()
        return list.map { mapper.map(it) }
    }

    suspend fun createCategoryRemote(category: Category): BasicResult {
        val model = mapper.mapToCreatingModel(category)
        val response = remoteSource.createCategory(model)
        return resultMapper.map(response)
    }

    suspend fun updateCategoryRemote(category: Category): BasicResult {
        val model = mapper.mapToEditingModel(category)
        val response = remoteSource.updateCategory(model)
        return resultMapper.map(response)
    }

    suspend fun deleteCategoryRemote(id: Long): BasicResult {
        val response = remoteSource.deleteCategoryById(id)
        return resultMapper.map(response)
    }
}