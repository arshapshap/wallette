package com.example.data.mappers

import com.example.common.domain.models.Category
import com.example.common.domain.models.enums.CategoryIcon
import com.example.common.domain.models.enums.TransactionType
import com.example.core_db.models.entities.CategoryLocal
import com.example.core_network.data.models.request.category.CategoryCreatingModel
import com.example.core_network.data.models.request.category.CategoryEditingModel
import com.example.core_network.data.models.response.CategoryResponse
import javax.inject.Inject

class CategoryMapper @Inject constructor() {

    fun map(response: CategoryResponse?): Category {
        return response?.let {
            Category(
                id = response.id ?: 0,
                name = response.name ?: "",
                icon = CategoryIcon.valueOf(response.icon ?: "empty"),
                type = TransactionType.valueOf(response.type ?: "")
            )
        } ?: Category(
            id = 0,
            name = "",
            icon = CategoryIcon.Empty,
            type = TransactionType.Expense
        )
    }

    fun map(local: CategoryLocal): Category {
        return with (local) {
            Category(
                id = categoryId,
                name = name,
                icon = CategoryIcon.valueOf(icon),
                type = TransactionType.valueOf(type)
            )
        }
    }

    fun map(category: Category): CategoryLocal {
        return with (category) {
            CategoryLocal(
                categoryId = id,
                name = name,
                icon = icon.name,
                type = type.name
            )
        }
    }

    fun mapToCreatingModel(category: Category): CategoryCreatingModel {
        return with (category) {
            CategoryCreatingModel(
                id = id.toString(),
                name = name,
                icon = icon.name,
                type = type.name
            )
        }
    }

    fun mapToEditingModel(category: Category): CategoryEditingModel {
        return with (category) {
            CategoryEditingModel(
                id = id.toString(),
                name = name,
                icon = icon.name
            )
        }
    }
}