package com.example.feature_auth.data.di

import com.example.feature_auth.data.network.AuthorizationApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class DataModule {

    @Provides
    fun provideAuthorizationApiService(retrofit: Retrofit): AuthorizationApiService {
        return retrofit.create(
            AuthorizationApiService::class.java
        )
    }
}