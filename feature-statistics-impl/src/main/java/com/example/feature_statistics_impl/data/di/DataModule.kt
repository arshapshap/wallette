package com.example.feature_statistics_impl.data.di

import com.example.feature_statistics_impl.data.network.TransactionsApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class DataModule {

    @Provides
    fun provideTransactionsApiService(retrofit: Retrofit): TransactionsApiService {
        return retrofit.create(
            TransactionsApiService::class.java
        )
    }
}