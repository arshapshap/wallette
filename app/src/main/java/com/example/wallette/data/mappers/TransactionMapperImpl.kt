package com.example.wallette.data.mappers

import com.example.common.data.mappers.AccountMapper
import com.example.common.data.mappers.CategoryMapper
import com.example.common.data.mappers.TagMapper
import com.example.common.data.mappers.TransactionMapper
import com.example.common.data.models.response.TransactionResponse
import com.example.common.domain.models.Transaction
import com.example.common.domain.models.TransactionType
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class TransactionMapperImpl @Inject constructor(
    private val accountMapper: AccountMapper,
    private val categoryMapper: CategoryMapper,
    private val tagMapper: TagMapper
) : TransactionMapper {

   override fun map(response: TransactionResponse): Transaction {
        return Transaction(
            id = response.id ?: "",
            type = TransactionType.valueOf(response.type ?: ""),
            date = getDateFromString(response.date ?: ""),
            amount = response.amount ?: 0,
            description = response.description ?: "",
            account = accountMapper.map(response.account),
            category = response.category?.let { categoryMapper.map(it) },
            transactionGroup = response.transactionGroup?.let { map(it) },
            isTransactionGroup = response.isTransactionGroup ?: false,
            tags = response.tags?.map { tagMapper.map(it) } ?: listOf()
        )
    }

    private fun getDateFromString(string: String): Date {
        return SimpleDateFormat("yyyy-MM-dd").parse(string) ?: Date()
    }
}