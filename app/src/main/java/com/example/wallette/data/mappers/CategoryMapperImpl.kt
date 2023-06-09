package com.example.wallette.data.mappers

import com.example.common.data.mappers.CategoryMapper
import com.example.common.data.models.response.CategoryResponse
import com.example.common.domain.models.Category
import com.example.common.domain.models.CategoryIcon
import com.example.common.domain.models.TransactionType
import javax.inject.Inject

class CategoryMapperImpl @Inject constructor() : CategoryMapper {

    override fun map(response: CategoryResponse?): Category {
        return response?.let {
            Category(
                id = response.id ?: "",
                name = response.name ?: "",
                icon = CategoryIcon.valueOf(response.icon ?: "empty"),
                type = TransactionType.valueOf(response.type ?: "")
            )
        } ?: Category(
            id = "",
            name = "",
            icon = CategoryIcon.Empty,
            type = TransactionType.Expense
        )
    }
}