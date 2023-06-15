package com.example.feature_statistics_impl.presentation

import com.example.common.domain.models.Transaction
import java.util.Date

interface StatisticsRouter {
    fun openTransactions()

    fun openTransactionsByPeriod(start: Date?, end: Date?)

    fun openSingleTransaction(transaction: Transaction?)

    fun openStatistics()

    fun close()
}