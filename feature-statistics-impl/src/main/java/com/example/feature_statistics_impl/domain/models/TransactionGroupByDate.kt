package com.example.feature_statistics_impl.domain.models

import com.example.common.domain.models.Transaction
import java.util.*

data class TransactionGroupByDate(
    val date: Date,
    override val list: List<Transaction>,
    override var isExpanded: Boolean = true
) : TransactionGroup

