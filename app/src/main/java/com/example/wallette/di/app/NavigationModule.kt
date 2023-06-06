package com.example.wallette.di.app

import com.example.common.di.scopes.ApplicationScope
import com.example.feature_auth.presentation.screen.AuthorizationRouter
import com.example.feature_settings.presentation.SettingsRouter
import com.example.feature_statistics_impl.presentation.StatisticsRouter
import com.example.wallette.navigation.Navigator
import dagger.Module
import dagger.Provides

@Module
class NavigationModule {

    @ApplicationScope
    @Provides
    fun provideNavigator(): Navigator = Navigator()

    @ApplicationScope
    @Provides
    fun provideStatisticsRouter(navigator: Navigator): StatisticsRouter = navigator

    @ApplicationScope
    @Provides
    fun provideAuthorizationRouter(navigator: Navigator): AuthorizationRouter = navigator

    @ApplicationScope
    @Provides
    fun provideSettingsRouter(navigator: Navigator): SettingsRouter = navigator
}