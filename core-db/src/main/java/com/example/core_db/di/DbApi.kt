package com.example.core_db.di

import com.example.core_db.AppDatabase

interface DbApi {

    fun provideDatabase(): AppDatabase
}