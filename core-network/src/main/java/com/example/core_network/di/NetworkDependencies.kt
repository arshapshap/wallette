package com.example.core_network.di

import android.content.Context
import android.content.SharedPreferences

interface NetworkDependencies {

    fun context(): Context

    fun sharedPreferences(): SharedPreferences
}