package com.example.feature_statistics_impl.domain.models

import com.example.common.domain.models.Tag
import com.example.common.domain.models.Transaction

data class TransactionGroupByTag(
    val tag: Tag?,
    override val list: List<Transaction>,
    override var isExpanded: Boolean = false
) : TransactionGroup