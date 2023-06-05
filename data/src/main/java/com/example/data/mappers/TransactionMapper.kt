package com.example.data.mappers

import com.example.common.domain.models.Transaction
import com.example.common.domain.models.enums.TransactionType
import com.example.common.presentation.extensions.roundToDay
import com.example.core_db.models.FullTransactionLocal
import com.example.core_db.models.entities.TransactionLocal
import com.example.core_network.data.models.request.transaction.TransactionCreatingModel
import com.example.core_network.data.models.request.transaction.TransactionEditingModel
import com.example.core_network.data.models.response.TransactionResponse
import com.example.data.mappers.TransactionMapper.DateHelper.getDateFromIso
import com.example.data.mappers.TransactionMapper.DateHelper.toIsoString
import java.text.DateFormat
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
            date = response.date?.getDateFromIso() ?: Date(),
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
                categoryId = category?.id
            )
        }
    }

    fun mapToCreatingModel(transaction: Transaction): TransactionCreatingModel {
        return with (transaction) {
            TransactionCreatingModel(
                id = id,
                type = type.name,
                date = date.toIsoString(),
                amount = amount,
                description = description,
                accountId = account.id,
                accountDestinationId = accountDestination?.id,
                categoryId = category?.id,
                tagsId = tags.map { it.id }
            )
        }
    }

    fun mapToEditingModel(transaction: Transaction): TransactionEditingModel {
        return with (transaction) {
            TransactionEditingModel(
                id = id,
                date = date.toIsoString(),
                amount = amount,
                description = description,
                accountId = account.id,
                accountDestinationId = accountDestination?.id,
                categoryId = category?.id,
                tagsId = tags.map { it.id }
            )
        }
    }

    object DateHelper {
        private const val ISO_8601_24H_FULL_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"

        fun Date.toIsoString(): String {
            val dateFormat: DateFormat = SimpleDateFormat(ISO_8601_24H_FULL_FORMAT)
            return dateFormat.format(this)
        }

        fun String.getDateFromIso(): Date {
            return SimpleDateFormat(ISO_8601_24H_FULL_FORMAT).parse(this) ?: Date()
        }
    }
}