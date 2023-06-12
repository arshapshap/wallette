package com.example.feature_statistics_impl.domain.utils

import com.example.common.domain.models.Tag
import com.example.common.domain.models.Transaction
import com.example.common.domain.models.enums.DayOfWeek
import com.example.common.domain.models.enums.TimePeriod
import com.example.common.presentation.extensions.copy
import com.example.common.presentation.extensions.roundToDay
import com.example.feature_statistics_impl.domain.models.TransactionGroupByCategory
import com.example.feature_statistics_impl.domain.models.TransactionGroupByDate
import com.example.feature_statistics_impl.domain.models.TransactionGroupByPeriod
import com.example.feature_statistics_impl.domain.models.TransactionGroupByTag
import java.util.*
import kotlin.math.absoluteValue

internal fun List<Transaction>.filterByViewedAccount(viewedAccountId: Long?): List<Transaction> {
    if (viewedAccountId != null)
        return this.filter {
            it.account.id == viewedAccountId || it.accountDestination?.id == viewedAccountId
        }
    return this
}

internal fun List<Transaction>.groupByTimePeriod(timePeriod: TimePeriod, firstDayOfWeek: DayOfWeek, firstDayOfMonth: Int): List<TransactionGroupByPeriod> {
    return when (timePeriod) {
        TimePeriod.Day -> this.groupByDay()
        TimePeriod.Week -> this.groupByWeek(firstDayOfWeek)
        TimePeriod.Month -> this.groupByMonth(firstDayOfMonth)
        TimePeriod.Year -> this.groupByYear()
        TimePeriod.All -> listOf(
            TransactionGroupByPeriod(
                list = this
            )
        )
    }.dropWhile { it.list.isEmpty() }
}

internal fun List<Transaction>.groupByDay(): List<TransactionGroupByPeriod> {
    val result = mutableListOf<TransactionGroupByPeriod>()
    var currentList = mutableListOf<Transaction>()

    val firstTransactionDate = this.firstOrNull()?.date ?: return listOf()
    val calendar = Calendar.getInstance().apply {
        time = firstTransactionDate
    }

    this.forEach {
        while (!it.date.before(calendar.time)) {
            result.add(
                TransactionGroupByPeriod(
                    periodStart = calendar.copy(),
                    list = currentList,
                )
            )
            currentList = mutableListOf()
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }
        currentList.add(it)
    }
    if (currentList.isNotEmpty()) {
        result.add(
            TransactionGroupByPeriod(
                periodStart = calendar.copy(),
                list = currentList,
            )
        )
    }

    return result
}

internal fun List<Transaction>.groupByWeek(firstDayOfWeek: DayOfWeek): List<TransactionGroupByPeriod> {
    val result = mutableListOf<TransactionGroupByPeriod>()
    var currentList = mutableListOf<Transaction>()

    val firstTransactionDate = this.firstOrNull()?.date ?: return listOf()
    val weekStartCalendar = Calendar.getInstance().apply {
        time = firstTransactionDate
    }
    do {
        weekStartCalendar.add(Calendar.DAY_OF_MONTH, -1)
    } while (weekStartCalendar.get(Calendar.DAY_OF_WEEK) - 1 != firstDayOfWeek.ordinal)

    val weekEndCalendar = weekStartCalendar.copy().apply {
        add(Calendar.WEEK_OF_YEAR, 1)
    }

    this.forEach {
        while (!it.date.after(weekStartCalendar.time) || !it.date.before(weekEndCalendar.time)) {
            result.add(
                TransactionGroupByPeriod(
                    periodStart = weekStartCalendar.copy(),
                    periodEnd = weekEndCalendar.copy(),
                    list = currentList,
                )
            )
            currentList = mutableListOf()
            weekStartCalendar.add(Calendar.WEEK_OF_YEAR, 1)
            weekEndCalendar.add(Calendar.WEEK_OF_YEAR, 1)
        }
        currentList.add(it)
    }
    if (currentList.isNotEmpty()) {
        result.add(
            TransactionGroupByPeriod(
                periodStart = weekStartCalendar.copy(),
                periodEnd = weekEndCalendar.copy(),
                list = currentList,
            )
        )
    }

    return result
}

internal fun List<Transaction>.groupByMonth(firstDayOfMonth: Int): List<TransactionGroupByPeriod> {
    val result = mutableListOf<TransactionGroupByPeriod>()
    var currentList = mutableListOf<Transaction>()

    val firstTransactionDate = this.firstOrNull()?.date ?: return listOf()
    val monthStartCalendar = Calendar.getInstance().apply {
        time = firstTransactionDate
    }
    do {
        monthStartCalendar.add(Calendar.DAY_OF_MONTH, -1)
    } while (monthStartCalendar.get(Calendar.DAY_OF_MONTH) != firstDayOfMonth)

    val monthEndCalendar = monthStartCalendar.copy().apply {
        add(Calendar.MONTH, 1)
        add(Calendar.DAY_OF_MONTH, 1) // почему без этого не работает???
        add(Calendar.DAY_OF_MONTH, -1)
    }

    this.forEach {
        while ((!it.date.after(monthStartCalendar.time) || !it.date.before(monthEndCalendar.time))
            && it.date != monthStartCalendar.time) {
            result.add(
                TransactionGroupByPeriod(
                    periodStart = monthStartCalendar.copy(),
                    periodEnd = monthEndCalendar.copy(),
                    list = currentList,
                )
            )
            currentList = mutableListOf()
            monthStartCalendar.add(Calendar.MONTH, 1)
            monthEndCalendar.add(Calendar.MONTH, 1)
        }
        currentList.add(it)
    }
    if (currentList.isNotEmpty()) {
        result.add(
            TransactionGroupByPeriod(
                periodStart = monthStartCalendar.copy(),
                periodEnd = monthEndCalendar.copy(),
                list = currentList,
            )
        )
    }

    return result
}

internal fun List<Transaction>.groupByYear(): List<TransactionGroupByPeriod> {
    val result = mutableListOf<TransactionGroupByPeriod>()
    var currentList = mutableListOf<Transaction>()

    val firstTransactionDate = this.firstOrNull()?.date ?: return listOf()
    val yearStartCalendar = Calendar.getInstance().apply {
        time = firstTransactionDate
    }
    do {
        yearStartCalendar.add(Calendar.DAY_OF_YEAR, -1)
    } while (yearStartCalendar.get(Calendar.DAY_OF_YEAR) != 1)

    val yearEndCalendar = yearStartCalendar.copy().apply {
        add(Calendar.YEAR, 1)
    }

    this.forEach {
        while (!it.date.after(yearStartCalendar.time) || !it.date.before(yearEndCalendar.time)) {
            result.add(
                TransactionGroupByPeriod(
                    periodStart = yearStartCalendar.copy(),
                    periodEnd = yearEndCalendar.copy(),
                    list = currentList,
                )
            )
            currentList = mutableListOf()
            yearStartCalendar.add(Calendar.YEAR, 1)
            yearEndCalendar.add(Calendar.YEAR, 1)
        }
        currentList.add(it)
    }
    if (currentList.isNotEmpty()) {
        result.add(
            TransactionGroupByPeriod(
                periodStart = yearStartCalendar.copy(),
                periodEnd = yearEndCalendar.copy(),
                list = currentList,
            )
        )
    }

    return result
}

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
        .sortedWith(compareBy<TransactionGroupByCategory> { it.list.sumOf { it.amount } < 0 }
            .thenByDescending { it.list.sumOf { it.amount }.absoluteValue })
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
        .sortedWith(compareBy<TransactionGroupByTag> { it.list.sumOf { it.amount } < 0 }
            .thenByDescending { it.list.sumOf { it.amount }.absoluteValue })
}

internal fun List<Transaction>.sortTransactionsByAmount(): List<Transaction> {
    return this
        .sortedWith(compareBy<Transaction> { it.amount < 0 }
            .thenByDescending { it.amount.absoluteValue })
}