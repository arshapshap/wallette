package com.example.wallette

import android.app.Application
import com.example.wallette.di.app.AppComponent
import com.example.wallette.di.app.AppModule
import com.example.wallette.di.app.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }
}