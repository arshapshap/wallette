package com.example.feature_settings.domain.repositories

import com.example.common.domain.models.Category

interface CategoriesRepository {

    suspend fun getCategories(): List<Category>
}