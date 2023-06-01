package com.example.feature_statistics_impl.di

import com.example.feature_statistics_impl.data.repositories.TransactionsRepositoryRandomImpl
import com.example.feature_statistics_impl.domain.repositories.TransactionsRepository
import dagger.Binds
import dagger.Module

@Module
interface StatisticsBindsModule {

    @Binds
    fun bindTransactionsRepository(repository: TransactionsRepositoryRandomImpl): TransactionsRepository
    // TODO: убрать рандом
}