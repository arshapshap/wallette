package com.example.di

import android.content.Context
import com.example.common.data.TokenManager
import com.example.core_db.AppDatabase
import com.example.core_network.data.services.AuthorizationApiService

interface DataDependencies {

    fun context(): Context

    fun appDatabase(): AppDatabase

    fun authorizationApiService(): AuthorizationApiService

    fun tokenManager(): TokenManager
}