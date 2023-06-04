package com.example.feature_statistics_impl.domain

import com.example.common.domain.models.*
import com.example.feature_statistics_impl.domain.repositories.TransactionsRepository
import com.example.feature_statistics_impl.presentation.screen.transactionsList.SortingType
import com.example.feature_statistics_impl.presentation.screen.transactionsList.groupsRecyclerView.transactionGroups.TransactionGroup
import com.example.feature_statistics_impl.presentation.screen.transactionsList.groupsRecyclerView.transactionGroups.TransactionGroupByCategory
import com.example.feature_statistics_impl.presentation.screen.transactionsList.groupsRecyclerView.transactionGroups.TransactionGroupByDate
import com.example.feature_statistics_impl.presentation.screen.transactionsList.groupsRecyclerView.transactionGroups.TransactionGroupByTag
import java.util.*
import javax.inject.Inject
import kotlin.math.absoluteValue
import kotlin.random.Random

class StatisticsInteractor @Inject constructor(
    private val repository: TransactionsRepository,
) {

    suspend fun getTransactionGroups(sortingType: SortingType): List<TransactionGroup> {
        val transactions = repository.getTransactions()
        val groups = when (sortingType) {
            SortingType.ByDate -> getGroupsByDate(transactions)
            SortingType.ByCategory -> getGroupsByCategory(transactions)
            SortingType.ByTag -> getGroupsByTag(transactions)
        }

        return groups
    }

    suspend fun createTransaction(transaction: Transaction) {
        // TODO: добавить функционал
    }

    suspend fun editTransaction(transaction: Transaction) {
        // TODO: добавить функционал
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
            .map {
                TransactionGroupByTag(
                    tag = it.key,
                    list = it.value.toList().sortTransactionsByAmount()
                )
            }
            .sortedWith(compareBy<TransactionGroupByTag> { it.list.sumOf { it.amount } < 0 }
                .thenByDescending { it.list.sumOf { it.amount }.absoluteValue })
    }

    fun getCategories(): List<Category> {
        // TODO: убрать рандом
        val list = arrayListOf<Category>()
        val rand = Random(1234)
        for (i in 0..10) {
            list.add(
                Category(
                    id = i.toString(),
                    name = "Категория $i",
                    icon = CategoryIcon.values().filter { it.name != "Empty" }.random(rand),
                    type = TransactionType.values().random(rand)
                )
            )
        }
        return list
    }

    fun getAccounts(): List<Account> {
        return listOf() // TODO: добавить данные
    }

    fun getTags(): List<Tag> {
        // TODO: убрать рандом
        val list = arrayListOf<Tag>()
        val rand = Random(1234)
        for (i in 0..10) {
            list.add(
                Tag(
                    id = i.toString(),
                    name = "Метка $i",
                    color = getRandomColor(rand)
                )
            )
        }
        return list
    }

    private fun getRandomColor(rand: Random): String {
        val r = rand.nextInt(256).toString(16).padStart(2, '0')
        val g = rand.nextInt(256).toString(16).padStart(2, '0')
        val b = rand.nextInt(256).toString(16).padStart(2, '0')
        return "#$r$g$b"
    }

    private fun List<Transaction>.sortTransactionsByAmount(): List<Transaction> {
        return this
            .sortedWith(compareBy<Transaction> { it.amount < 0 }
                .thenByDescending { it.amount.absoluteValue })
    }

    private fun Date.roundToDay(): Date {
        val calendar = Calendar.getInstance().apply {
            time = this@roundToDay
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return calendar.time
    }
}