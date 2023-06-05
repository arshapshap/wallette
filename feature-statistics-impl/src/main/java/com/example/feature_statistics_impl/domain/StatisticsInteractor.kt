package com.example.feature_statistics_impl.domain

import com.example.common.domain.models.Account
import com.example.common.domain.models.Category
import com.example.common.domain.models.Tag
import com.example.common.domain.models.Transaction
import com.example.common.domain.repositories.AccountRepository
import com.example.common.domain.repositories.CategoryRepository
import com.example.common.domain.repositories.TagRepository
import com.example.common.domain.repositories.TransactionRepository
import com.example.common.presentation.extensions.roundToDay
import com.example.feature_statistics_impl.presentation.screen.transactionsList.SortingType
import com.example.feature_statistics_impl.presentation.screen.transactionsList.groupsRecyclerView.transactionGroups.TransactionGroup
import com.example.feature_statistics_impl.presentation.screen.transactionsList.groupsRecyclerView.transactionGroups.TransactionGroupByCategory
import com.example.feature_statistics_impl.presentation.screen.transactionsList.groupsRecyclerView.transactionGroups.TransactionGroupByDate
import com.example.feature_statistics_impl.presentation.screen.transactionsList.groupsRecyclerView.transactionGroups.TransactionGroupByTag
import java.util.*
import javax.inject.Inject
import kotlin.math.absoluteValue

class StatisticsInteractor @Inject constructor(
    private val accountRepository: AccountRepository,
    private val categoryRepository: CategoryRepository,
    private val tagRepository: TagRepository,
    private val transactionRepository: TransactionRepository,
) {

    suspend fun getTransactionGroups(sortingType: SortingType): List<TransactionGroup> {
        val transactions = transactionRepository.getTransactions()

        val groups = when (sortingType) {
            SortingType.ByDate -> getGroupsByDate(transactions)
            SortingType.ByCategory -> getGroupsByCategory(transactions)
            SortingType.ByTag -> getGroupsByTag(transactions)
        }
        return groups
    }

    suspend fun createTransaction(transaction: Transaction) {
        transactionRepository.createTransaction(transaction)
    }

    suspend fun editTransaction(transaction: Transaction) {
        transactionRepository.updateTransaction(transaction)
    }

    suspend fun deleteTransaction(transaction: Transaction) {
        transactionRepository.deleteTransaction(transaction)
    }

    suspend fun getCategories(): List<Category> {
        return categoryRepository.getCategories()
    }

    suspend fun getAccounts(): List<Account> {
        return accountRepository.getAccounts()
    }

    suspend fun getTags(): List<Tag> {
        return tagRepository.getTags()
    }

    private fun getGroupsByDate(transactions: List<Transaction>): List<TransactionGroupByDate> {
        return transactions
            .groupBy { it.date.roundToDay() }
            .map {
                TransactionGroupByDate(
                    date = it.key,
                    list = it.value.sortTransactionsByAmount(),
                    isExpanded = it.key.roundToDay() == Calendar.getInstance().time.roundToDay()
                )
            }
            .sortedByDescending { it.date }
    }

    private fun getGroupsByCategory(transactions: List<Transaction>): List<TransactionGroupByCategory> {
        return transactions
            .groupBy { Pair(it.category, it.amount >= 0) }
            .map {
                TransactionGroupByCategory(
                    category = it.key.first,
                    list = it.value.sortedByDescending { it.date }
                )
            }
            .sortedWith(compareBy<TransactionGroupByCategory> { it.list.sumOf { it.amount } < 0 }
                .thenByDescending { it.list.sumOf { it.amount }.absoluteValue })
    }

    private fun getGroupsByTag(transactions: List<Transaction>): List<TransactionGroupByTag> {
        val groups = mutableMapOf<Tag?, ArrayList<Transaction>>(null to arrayListOf())
        transactions.forEach { transaction ->
            transaction.tags.forEach { tag ->
                if (!groups.containsKey(tag))
                    groups[tag] = arrayListOf()
                groups[tag]!!.add(transaction)
            }
            if (transaction.tags.isEmpty())
                groups[null]!!.add(transaction)
        }
        return groups
            .filter { it.value.isNotEmpty() }
            .map {
                TransactionGroupByTag(
                    tag = it.key,
                    list = it.value.toList().sortTransactionsByAmount()
                )
            }
            .sortedWith(compareBy<TransactionGroupByTag> { it.list.sumOf { it.amount } < 0 }
                .thenByDescending { it.list.sumOf { it.amount }.absoluteValue })
    }

    private fun List<Transaction>.sortTransactionsByAmount(): List<Transaction> {
        return this
            .sortedWith(compareBy<Transaction> { it.amount < 0 }
                .thenByDescending { it.amount.absoluteValue })
    }
}