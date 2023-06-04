package com.example.di

import com.example.common.domain.repositories.*

interface DataApi {

    fun provideAccountsRepository(): AccountRepository

    fun provideAuthorizationRepository(): AuthorizationRepository

    fun provideCategoriesRepository(): CategoryRepository

    fun provideTagsRepository(): TagRepository

    fun provideTransactionsRepository(): TransactionRepository
}