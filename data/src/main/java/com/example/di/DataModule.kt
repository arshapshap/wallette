package com.example.di

import com.example.common.di.scopes.ApplicationScope
import com.example.core_db.AppDatabase
import com.example.core_db.dao.*
import com.google.gson.Gson
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    @ApplicationScope
    fun provideGson(): Gson
        = Gson()

    @Provides
    @ApplicationScope
    fun provideAccountDao(appDatabase: AppDatabase): AccountDao
        = appDatabase.accountDao()

    @Provides
    @ApplicationScope
    fun provideCategoryDao(appDatabase: AppDatabase): CategoryDao
            = appDatabase.categoryDao()

    @Provides
    @ApplicationScope
    fun provideTagDao(appDatabase: AppDatabase): TagDao
            = appDatabase.tagDao()

    @Provides
    @ApplicationScope
    fun provideTransactionDao(appDatabase: AppDatabase): TransactionDao
            = appDatabase.transactionDao()

    @Provides
    @ApplicationScope
    fun provideSyncRequestDao(appDatabase: AppDatabase): SyncRequestDao
            = appDatabase.syncRequestsDao()
}