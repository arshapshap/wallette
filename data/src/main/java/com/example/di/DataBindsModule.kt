package com.example.di

import com.example.common.domain.repositories.*
import com.example.data.repositories.*
import dagger.Binds
import dagger.Module

@Module
interface DataBindsModule {

    @Binds
    fun bindAccountsRepository(repositoryImpl: AccountRepositoryImpl): AccountRepository

    @Binds
    fun bindAuthorizationRepository(repositoryImpl: AuthorizationRepositoryImpl): AuthorizationRepository

    @Binds
    fun bindCategoriesRepository(repositoryImpl: CategoryRepositoryImpl): CategoryRepository

    @Binds
    fun bindTagsRepository(repositoryImpl: TagRepositoryImpl): TagRepository

    @Binds
    fun bindTransactionsRepository(repository: TransactionRepositoryImpl): TransactionRepository
}