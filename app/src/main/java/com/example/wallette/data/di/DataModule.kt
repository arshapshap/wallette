package com.example.wallette.data.di

import android.content.Context
import android.content.SharedPreferences
import com.example.common.di.scopes.ApplicationScope
import dagger.Module
import dagger.Provides

@Module(includes = [NetworkModule::class])
class DataModule {

    @ApplicationScope
    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
    }
}