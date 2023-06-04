package com.example.core_network.di

import com.example.common.data.TokenManager
import com.example.core_network.data.services.AuthorizationApiService

interface NetworkApi {

    fun authorizationApiService(): AuthorizationApiService

    fun tokenManager(): TokenManager
}