package com.example.common.domain.models

import java.util.*

data class Transaction(
    val id: String,
    val type: TransactionType,
    val date: Date,
    val amount: Double,
    val description: String,
    val account: Account,
    val accountDestination: Account?,
    val category: Category?,
    val transactionGroup: Transaction?,
    val isTransactionGroup: Boolean,
    val tags: List<Tag>
)