package com.example.common.data.mappers

import com.example.common.data.models.response.CategoryResponse
import com.example.common.domain.models.Category

interface CategoryMapper {

    fun map(response: CategoryResponse?): Category
}