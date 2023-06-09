package com.example.data.repositories.remote

import com.example.common.domain.models.Category
import com.example.common.domain.models.network.BasicResult
import com.example.core_network.data.services.CategoriesApiService
import com.example.data.managers.interfaces.CategoryRemoteRepository
import com.example.data.mappers.BasicResultMapper
import com.example.data.mappers.CategoryMapper
import javax.inject.Inject

class CategoryRemoteRepositoryImpl @Inject constructor(
    private val remoteSource: CategoriesApiService,
    private val mapper: CategoryMapper,
    private val resultMapper: BasicResultMapper
): CategoryRemoteRepository {

    override suspend fun createCategoryRemote(category: Category): BasicResult {
        val model = mapper.mapToCreatingModel(category)
        val response = remoteSource.createCategory(model)
        return resultMapper.map(response)
    }

    override suspend fun updateCategoryRemote(category: Category): BasicResult {
        val model = mapper.mapToEditingModel(category)
        val response = remoteSource.updateCategory(model)
        return resultMapper.map(response)
    }

    override suspend fun deleteCategoryRemote(category: Category): BasicResult {
        val response = remoteSource.deleteCategoryById(category.id)
        return resultMapper.map(response)
    }
}