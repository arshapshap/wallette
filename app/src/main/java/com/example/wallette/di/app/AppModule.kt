package com.example.wallette.di.app

import android.content.Context
import com.example.wallette.App
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideContext(app: App): Context {
        return app
    }
}