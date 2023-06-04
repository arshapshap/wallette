package com.example.feature_statistics_impl.presentation.screen.singleTransaction

import com.example.common.domain.models.*
import java.util.*

data class EditingTransaction(
    val id: String = "",
    val type: TransactionType = TransactionType.Expense,
    val date: Date? = null,
    val amount: Double? = null,
    val description: String = "",
    val account: Account? = null,
    val accountDestination: Account? = null,
    val category: Category? = null,
    val transactionGroup: Transaction? = null,
    val isTransactionGroup: Boolean? = null,
    val tags: List<Tag> = listOf()
)