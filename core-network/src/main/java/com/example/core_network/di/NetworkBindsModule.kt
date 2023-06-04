package com.example.core_network.di

import com.example.common.data.TokenManager
import com.example.core_network.data.managers.TokenManagerImpl
import dagger.Binds
import dagger.Module

@Module
interface NetworkBindsModule {

    @Binds
    fun bindTokenManager(tokenManagerImpl: TokenManagerImpl): TokenManager
}