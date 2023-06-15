package com.example.feature_statistics_impl.domain.models

import java.util.*

data class TransactionGroupByPeriod(
    val periodStart: Date? = null,
    val periodEnd: Date? = null,
    val list: List<TransactionShortInfo>,
    val income: Double,
    val expense: Double
)