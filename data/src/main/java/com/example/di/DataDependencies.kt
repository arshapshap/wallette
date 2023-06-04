package com.example.di

import com.example.core_db.AppDatabase

interface DataDependencies {

    fun appDatabase(): AppDatabase
}