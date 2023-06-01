package com.example.feature_statistics_impl

interface StatisticsRouter {
    fun openTransactions()

    fun openTransaction(id: String)
}