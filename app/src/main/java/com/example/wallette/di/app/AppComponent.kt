package com.example.wallette.di.app

import com.example.common.di.scopes.ApplicationScope
import com.example.wallette.App
import com.example.wallette.di.main.MainDependencies
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        AppModule::class,
        NavigationModule::class,
        FeaturesModule::class,
        FeatureManagerModule::class,
        ComponentDependenciesModule::class
    ])
interface AppComponent : MainDependencies {

    companion object {

        fun init(application: App): AppComponent {
            return DaggerAppComponent
                .builder()
                .application(application)
                .build()
        }
    }

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: App): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}