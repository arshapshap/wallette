package com.example.core_network.di

import com.example.common.data.TokenManager
import com.example.core_network.data.services.*

interface NetworkApi {

    fun accountsApiService(): AccountsApiService

    fun authorizationApiService(): AuthorizationApiService

    fun categoriesApiService(): CategoriesApiService

    fun tagsApiService(): TagsApiService

    fun transactionsApiService(): TransactionsApiService

    fun tokenManager(): TokenManager
}