package com.example.feature_auth.data.di

import com.example.feature_auth.data.network.AuthorizationService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class DataModule {

    @Provides
    fun provideAuthorizationService(retrofit: Retrofit): AuthorizationService {
        return retrofit.create(
            AuthorizationService::class.java
        )
    }
}