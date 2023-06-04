package com.example.di

import com.example.common.di.scopes.ApplicationScope
import com.example.core_db.AppDatabase
import com.example.core_db.dao.AccountDao
import com.example.core_db.dao.CategoryDao
import com.example.core_db.dao.TagDao
import com.example.core_db.dao.TransactionDao
import dagger.Module
import dagger.Provides

@Module
class DataModule {

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
}