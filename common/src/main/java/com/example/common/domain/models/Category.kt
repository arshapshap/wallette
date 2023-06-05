package com.example.common.domain.models

import com.example.common.domain.models.enums.CategoryIcon
import com.example.common.domain.models.enums.TransactionType
import java.io.Serializable

data class Category(
    val id: Long,
    val name: String,
    val icon: CategoryIcon,
    val type: TransactionType
) : Serializable
