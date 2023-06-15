package com.example.feature_statistics_impl.domain.models

import com.example.common.domain.models.Category
import com.example.common.domain.models.enums.TransactionType

data class TransactionShortInfo(
    val amount: Double,
    val category: Category?,
    val type: TransactionType
)
