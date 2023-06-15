package com.example.feature_statistics_impl.domain.utils

import com.example.common.domain.models.Tag
import com.example.common.domain.models.Transaction
import com.example.common.domain.models.enums.DayOfWeek
import com.example.common.domain.models.enums.TimePeriod
import com.example.common.domain.models.enums.TransactionType
import com.example.common.presentation.extensions.between
import com.example.common.presentation.extensions.copy
import com.example.common.presentation.extensions.roundToDay
import com.example.feature_statistics_impl.domain.models.*
import java.util.*
import kotlin.math.absoluteValue

internal fun List<Transaction>.filterByViewedAccount(viewedAccountId: Long?): List<Transaction> {
    if (viewedAccountId != null)
        return this.filter {
            it.account.id == viewedAccountId || it.accountDestination?.id == viewedAccountId
        }.map {
            if (it.type == TransactionType.Transfer && it.account.id == viewedAccountId)
                it.copy(amount = it.amount * -1)
            else
                it
        }
    return this.filter { it.type != TransactionType.Transfer }
}

internal fun List<Transaction>.filterExpenses(viewedAccountId: Long?): List<Transaction> {
    return this.filter {
        it.type == TransactionType.Expense
                || (it.type == TransactionType.Transfer
                && it.account.id == viewedAccountId)
    }
}

internal fun List<Transaction>.filterIncomes(viewedAccountId: Long?): List<Transaction> {
    return this.filter {
        it.type == TransactionType.Income
                || (it.type == TransactionType.Transfer
                && it.accountDestination?.id == viewedAccountId)
    }
}

internal fun List<Transaction>.sum(): Double
    = this.sumOf { it.amount }

internal fun List<Transaction>.groupByTimePeriod(viewedAccountId: Long?, timePeriod: TimePeriod, firstDayOfWeek: DayOfWeek, firstDayOfMonth: Int): List<TransactionGroupByPeriod> {
    return when (timePeriod) {
        TimePeriod.Day -> this.groupByDay(viewedAccountId)
        TimePeriod.Week -> this.groupByWeek(viewedAccountId, firstDayOfWeek)
        TimePeriod.Month -> this.groupByMonth(viewedAccountId, firstDayOfMonth)
        TimePeriod.Year -> this.groupByYear(viewedAccountId)
        TimePeriod.All -> listOf(
            getTransactionGroupByPeriod(
                list = this,
                viewedAccountId = viewedAccountId
            )
        )
    }
}

internal fun List<Transaction>.groupByDay(viewedAccountId: Long?): List<TransactionGroupByPeriod> {
    val firstTransactionDate = this.firstOrNull()?.date ?: Calendar.getInstance().time
    val calendar = Calendar.getInstance().apply {
        time = firstTransactionDate
    }

    return getGroupsByPeriod(
        list = this,
        startCalendar = calendar,
        endCalendar = calendar.copy(),
        calendarAttribute = Calendar.DAY_OF_YEAR,
        viewedAccountId = viewedAccountId
    )
}

internal fun List<Transaction>.groupByWeek(viewedAccountId: Long?, firstDayOfWeek: DayOfWeek): List<TransactionGroupByPeriod> {
    val firstTransactionDate = this.firstOrNull()?.date ?: Calendar.getInstance().time
    val weekStartCalendar = Calendar.getInstance().apply {
        time = firstTransactionDate
    }
    do {
        weekStartCalendar.add(Calendar.DAY_OF_MONTH, -1)
    } while (weekStartCalendar.get(Calendar.DAY_OF_WEEK) - 1 != firstDayOfWeek.ordinal)

    val weekEndCalendar = weekStartCalendar.copy().apply {
        add(Calendar.WEEK_OF_YEAR, 1)
        add(Calendar.DAY_OF_WEEK, -1)
    }

    return getGroupsByPeriod(
        list = this,
        startCalendar = weekStartCalendar,
        endCalendar = weekEndCalendar,
        calendarAttribute = Calendar.WEEK_OF_YEAR,
        viewedAccountId = viewedAccountId
    )
}

internal fun List<Transaction>.groupByMonth(viewedAccountId: Long?, firstDayOfMonth: Int): List<TransactionGroupByPeriod> {
    val firstTransactionDate = this.firstOrNull()?.date ?: Calendar.getInstance().time
    val monthStartCalendar = Calendar.getInstance().apply {
        time = firstTransactionDate
    }
    do {
        monthStartCalendar.add(Calendar.DAY_OF_MONTH, -1)
    } while (monthStartCalendar.get(Calendar.DAY_OF_MONTH) != firstDayOfMonth)

    val monthEndCalendar = monthStartCalendar.copy().apply {
        add(Calendar.MONTH, 1)
        add(Calendar.DAY_OF_MONTH, -1)
    }

    return getGroupsByPeriod(
        list = this,
        startCalendar = monthStartCalendar,
        endCalendar = monthEndCalendar,
        calendarAttribute = Calendar.MONTH,
        viewedAccountId = viewedAccountId
    )
}

internal fun List<Transaction>.groupByYear(viewedAccountId: Long?): List<TransactionGroupByPeriod> {
    val firstTransactionDate = this.firstOrNull()?.date ?: Calendar.getInstance().time
    val yearStartCalendar = Calendar.getInstance().apply {
        time = firstTransactionDate
    }
    do {
        yearStartCalendar.add(Calendar.DAY_OF_YEAR, -1)
    } while (yearStartCalendar.get(Calendar.DAY_OF_YEAR) != 1)

    val yearEndCalendar = yearStartCalendar.copy().apply {
        add(Calendar.YEAR, 1)
        add(Calendar.DAY_OF_YEAR, -1)
    }

    return getGroupsByPeriod(
        list = this,
        startCalendar = yearStartCalendar,
        endCalendar = yearEndCalendar,
        calendarAttribute = Calendar.YEAR,
        viewedAccountId = viewedAccountId
    )
}

private fun getGroupsByPeriod(
    list: List<Transaction>,
    startCalendar: Calendar,
    endCalendar: Calendar,
    calendarAttribute: Int,
    viewedAccountId: Long?
): List<TransactionGroupByPeriod> {
    val result = mutableListOf<TransactionGroupByPeriod>()
    val currentList = mutableListOf<Transaction>()
    val now = Calendar.getInstance().time

    var nowReached = false
    var index = 0
    while (index < list.size
        || !nowReached) {
        while (index < list.size && list[index].date.roundToDay().between(startCalendar.time, endCalendar.time)) {
            currentList.add(list[index])
            index++
        }
        result.add(
            getTransactionGroupByPeriod(
                periodStart = startCalendar.time,
                periodEnd = endCalendar.time,
                list = currentList,
                viewedAccountId = viewedAccountId
            )
        )
        currentList.clear()

        if (now.roundToDay().between(startCalendar.time, endCalendar.time))
            nowReached = true
        startCalendar.add(calendarAttribute, 1)
        endCalendar.add(calendarAttribute, 1)
    }

    return result
}

private fun getTransactionGroupByPeriod(
    periodStart: Date? = null,
    periodEnd: Date? = null,
    list: List<Transaction>,
    viewedAccountId: Long?
)   = TransactionGroupByPeriod(
    periodStart = periodStart,
    periodEnd = periodEnd,
    list = list.filterExpenses(viewedAccountId).map {
        TransactionShortInfo(
            amount = it.amount,
            category = it.category,
            type = it.type
        )
    },
    income = list.filterIncomes(viewedAccountId).sum(),
    expense = list.filterExpenses(viewedAccountId).sum()
)

internal fun List<Transaction>.groupByDate(): List<TransactionGroupByDate> {
    return this
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

internal fun List<Transaction>.groupByCategory(): List<TransactionGroupByCategory> {
    return this
        .groupBy { Pair(it.category, it.amount >= 0) }
        .map {
            TransactionGroupByCategory(
                category = it.key.first,
                list = it.value.sortedByDescending { it.date }
            )
        }
        .sortedWith(compareBy<TransactionGroupByCategory> { it.list.sum() < 0 }
            .thenByDescending { it.list.sum().absoluteValue })
}

internal fun List<Transaction>.groupByTag(): List<TransactionGroupByTag> {
    val groups = mutableMapOf<Tag?, ArrayList<Transaction>>(null to arrayListOf())
    this.forEach { transaction ->
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
        .sortedWith(compareBy<TransactionGroupByTag> { it.list.sum() < 0 }
            .thenByDescending { it.list.sum().absoluteValue })
}

internal fun List<Transaction>.sortTransactionsByAmount(): List<Transaction> {
    return this
        .sortedWith(compareBy<Transaction> { it.amount < 0 }
            .thenByDescending { it.amount.absoluteValue })
}