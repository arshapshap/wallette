package com.example.wallette

import android.app.Application
import com.example.wallette.di.app.AppComponent
import com.example.wallette.di.app.DaggerAppComponent

class App : Application() {

    private val appComponent: AppComponent by lazy {
        DaggerAppComponent
            .builder()
            .application(this)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
    }
}