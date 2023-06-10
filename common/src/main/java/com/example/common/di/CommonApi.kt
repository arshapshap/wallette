package com.example.common.di

import android.content.Context
import android.content.SharedPreferences

interface CommonApi {

    fun context(): Context

    fun sharedPreferences(): SharedPreferences
}