package com.example.feature_statistics_impl.domain.models

import com.example.common.domain.models.Transaction

interface TransactionGroup {
    val list: List<Transaction>
    var isExpanded: Boolean
}