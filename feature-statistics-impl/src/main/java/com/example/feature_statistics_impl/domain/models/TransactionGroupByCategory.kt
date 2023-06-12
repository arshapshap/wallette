package com.example.feature_statistics_impl.domain.models

import com.example.common.domain.models.Category
import com.example.common.domain.models.Transaction

data class TransactionGroupByCategory(
    val category: Category?,
    override val list: List<Transaction>,
    override var isExpanded: Boolean = false
) : TransactionGroup