package com.example.wallette.di.app

import com.example.common.data.TokenManager
import com.example.wallette.data.sharedPreferences.SharedPreferencesManager
import dagger.Binds
import dagger.Module

@Module
interface AppBindsModule {

    @Binds
    fun bindTokenManager(sharedPreferencesManager: SharedPreferencesManager): TokenManager
}