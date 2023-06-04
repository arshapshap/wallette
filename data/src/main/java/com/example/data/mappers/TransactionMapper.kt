package com.example.data.mappers

import com.example.core_network.data.models.response.TransactionResponse
import com.example.common.domain.models.Transaction
import com.example.common.domain.models.enums.TransactionType
import com.example.common.presentation.extensions.roundToDay
import com.example.core_db.models.FullTransactionLocal
import com.example.core_db.models.entities.TransactionLocal
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class TransactionMapper @Inject constructor(
    private val accountMapper: AccountMapper,
    private val categoryMapper: CategoryMapper,
    private val tagMapper: TagMapper
) {

   fun map(response: TransactionResponse): Transaction {
        return Transaction(
            id = response.id ?: 0,
            type = TransactionType.valueOf(response.type ?: ""),
            date = getDateFromString(response.date ?: ""),
            amount = response.amount ?: 0.0,
            description = response.description ?: "",
            account = accountMapper.map(response.account),
            accountDestination = response.account?.let { accountMapper.map(it) },
            category = response.category?.let { categoryMapper.map(it) },
            tags = response.tags?.map { tagMapper.map(it) } ?: listOf()
        )
    }

    fun map(local: FullTransactionLocal): Transaction {
        return with (local) {
            Transaction(
                id = transaction.transactionId,
                type = TransactionType.valueOf(transaction.type),
                date = Date(transaction.dateInDays),
                amount = transaction.amount,
                description = transaction.description,
                account = accountMapper.map(account),
                accountDestination = accountDestination?.let { accountMapper.map(it) },
                category = category?.let { categoryMapper.map(it) },
                tags = tags.map { tagMapper.map(it) }
            )
        }
    }

    fun map(transaction: Transaction): TransactionLocal {
        return with (transaction) {
            TransactionLocal(
                transactionId = id,
                type = type.name,
                dateInDays = date.roundToDay().time,
                amount = amount,
                description = description,
                accountId = account.id,
                accountDestinationId = accountDestination?.id,
                categoryId = category?.id,
                isSynchronized = false,
                mustBeDeleted = false,
            )
        }
    }

    private fun getDateFromString(string: String): Date {
        return SimpleDateFormat("yyyy-MM-dd").parse(string) ?: Date()
    }
}