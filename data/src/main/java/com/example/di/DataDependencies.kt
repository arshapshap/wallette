package com.example.di

import android.content.Context
import com.example.common.data.TokenManager
import com.example.core_db.AppDatabase
import com.example.core_network.data.services.*

interface DataDependencies {

    fun context(): Context

    fun appDatabase(): AppDatabase

    fun accountsApiService(): AccountsApiService

    fun authorizationApiService(): AuthorizationApiService

    fun categoriesApiService(): CategoriesApiService

    fun tagsApiService(): TagsApiService

    fun transactionsApiService(): TransactionsApiService

    fun tokenManager(): TokenManager
}