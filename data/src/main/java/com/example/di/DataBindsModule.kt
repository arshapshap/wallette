package com.example.di

import com.example.common.domain.repositories.*
import com.example.data.repositories.*
import dagger.Binds
import dagger.Module

@Module
interface DataBindsModule {

    @Binds
    fun bindTransactionsRepository(repository: TransactionRepositoryImpl): TransactionRepository

    @Binds
    fun bindAccountsRepository(repositoryImpl: AccountRepositoryImpl): AccountRepository

    @Binds
    fun bindCategoriesRepository(repositoryImpl: CategoryRepositoryImpl): CategoryRepository

    @Binds
    fun bindTagsRepository(repositoryImpl: TagRepositoryImpl): TagRepository
}