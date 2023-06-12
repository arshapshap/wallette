package com.example.feature_statistics_impl.domain.models

import com.example.common.domain.models.Transaction
import java.util.*

data class TransactionGroupByPeriod(
    val periodStart: Calendar? = null,
    val periodEnd: Calendar? = null,
    val list: List<Transaction>
)