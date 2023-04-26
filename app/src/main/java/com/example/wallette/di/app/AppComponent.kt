package com.example.wallette.di.app

import com.example.wallette.App
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class, NavigationModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: App): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}