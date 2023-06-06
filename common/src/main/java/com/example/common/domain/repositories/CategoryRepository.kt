package com.example.common.domain.repositories

import com.example.common.domain.models.Category

interface CategoryRepository {

    suspend fun createCategory(category: Category)

    suspend fun updateCategory(category: Category)

    suspend fun deleteCategory(category: Category)

    suspend fun getCategories(): List<Category>
}