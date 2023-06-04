package com.example.feature_statistics_impl.di

import com.example.common.domain.repositories.TransactionRepository

interface StatisticsDependencies {

    fun transactionsRepository(): TransactionRepository
}