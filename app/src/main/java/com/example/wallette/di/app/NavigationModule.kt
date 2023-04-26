package com.example.wallette.di.app

import com.example.feature_statistics_impl.StatisticsRouter
import com.example.wallette.navigation.Navigator
import dagger.Module
import dagger.Provides

@Module
class NavigationModule {

    @Provides
    fun provideNavigator(): Navigator = Navigator()

    @Provides
    fun provideStatisticsRouter(navigator: Navigator): StatisticsRouter = navigator
}