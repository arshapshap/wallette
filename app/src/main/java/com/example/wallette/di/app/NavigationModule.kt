package com.example.wallette.di.app

import com.example.wallette.navigation.Navigator
import dagger.Module
import dagger.Provides

@Module
class NavigationModule {
    @Provides
    fun provideNavigator(): Navigator = Navigator()
}