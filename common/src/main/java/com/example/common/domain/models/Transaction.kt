package com.example.common.domain.models

import com.example.common.domain.models.enums.TransactionType
import java.io.Serializable
import java.util.*

data class Transaction(
    val id: Long,
    val type: TransactionType,
    val date: Date,
    val amount: Double,
    val description: String,
    val account: Account,
    val accountDestination: Account?,
    val category: Category?,
    val tags: List<Tag>
) : Serializable