package com.example.feature_settings.data.repositories

import com.example.common.domain.models.Category
import com.example.common.domain.models.TransactionType
import com.example.feature_settings.domain.repositories.CategoriesRepository
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(): CategoriesRepository {

    override suspend fun getCategories(): List<Category> {
        val category1 = Category(
            id = "cat1",
            name = "Доход 1",
            icon = "",
            type = TransactionType.Income
        )
        val category2 = Category(
            id = "cat2",
            name = "Доход 2",
            icon = "",
            type = TransactionType.Income
        )
        val category3 = Category(
            id = "cat3",
            name = "Расход 1",
            icon = "",
            type = TransactionType.Expense
        )
        val category4 = Category(
            id = "cat4",
            name = "Расход 2",
            icon = "",
            type = TransactionType.Expense
        )
        return listOf(category1, category2, category3, category4, category1, category2, category3, category4, category1, category2, category3, category4, category1, category2, category3, category4)
    }
}