package com.example.di

import com.example.common.domain.repositories.*
import com.example.data.managers.interfaces.*
import com.example.data.repositories.*
import com.example.data.repositories.remote.AccountRemoteRepositoryImpl
import com.example.data.repositories.remote.CategoryRemoteRepositoryImpl
import com.example.data.repositories.remote.TagRemoteRepositoryImpl
import com.example.data.repositories.remote.TransactionRemoteRepositoryImpl
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

    @Binds
    fun bindAccountsRemoteRepository(repositoryImpl: AccountRemoteRepositoryImpl): AccountRemoteRepository

    @Binds
    fun bindCategoriesRemoteRepository(repositoryImpl: CategoryRemoteRepositoryImpl): CategoryRemoteRepository

    @Binds
    fun bindTagsRemoteRepository(repositoryImpl: TagRemoteRepositoryImpl): TagRemoteRepository

    @Binds
    fun bindTransactionsRemoteRepository(repository: TransactionRemoteRepositoryImpl): TransactionRemoteRepository
}