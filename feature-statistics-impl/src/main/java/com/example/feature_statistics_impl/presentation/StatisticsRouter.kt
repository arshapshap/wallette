package com.example.feature_statistics_impl.presentation

import com.example.common.domain.models.Transaction

interface StatisticsRouter {
    fun openTransactions()

    fun openSingleTransaction(transaction: Transaction?)

    fun openStatistics()

    fun close()
}