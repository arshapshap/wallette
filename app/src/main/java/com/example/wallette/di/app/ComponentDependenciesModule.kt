package com.example.wallette.di.app

import com.example.common.di.ComponentDependencies
import com.example.common.di.ComponentDependenciesKey
import com.example.wallette.di.main.MainDependencies
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ComponentDependenciesModule {

    @Binds
    @IntoMap
    @ComponentDependenciesKey(MainDependencies::class)
    fun provideMainDependencies(component: AppComponent): ComponentDependencies
}