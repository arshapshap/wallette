package com.example.feature_statistics_impl.di

import com.example.common.data.SettingsManager
import com.example.common.domain.repositories.AccountRepository
import com.example.common.domain.repositories.CategoryRepository
import com.example.common.domain.repositories.TagRepository
import com.example.common.domain.repositories.TransactionRepository

interface StatisticsDependencies {

    fun accountRepository(): AccountRepository

    fun categoryRepository(): CategoryRepository

    fun tagRepository(): TagRepository

    fun transactionRepository(): TransactionRepository

    fun settingsManager(): SettingsManager
}