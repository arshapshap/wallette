package com.example.wallette.di.app

import com.example.common.di.FeatureApiHolder
import com.example.common.di.FeatureContainer
import com.example.common.di.scopes.ApplicationScope
import com.example.feature_auth.di.AuthorizationFeatureApi
import com.example.feature_statistics_impl.di.AuthorizationFeatureHolder
import com.example.feature_statistics_impl.di.StatisticsFeatureApi
import com.example.feature_statistics_impl.di.StatisticsFeatureHolder
import com.example.wallette.App
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
interface FeaturesModule {

//    @ApplicationScope
//    @Binds
//    @ClassKey(StatisticsFeatureApi::class)
//    @IntoMap
//    fun statisticsComponent()

    @ApplicationScope
    @Binds
    fun provideFeatureContainer(application: App): FeatureContainer

    @ApplicationScope
    @Binds
    @ClassKey(StatisticsFeatureApi::class)
    @IntoMap
    fun provideStatisticsFeatureHolder(statisticsFeatureHolder: StatisticsFeatureHolder): FeatureApiHolder

    @ApplicationScope
    @Binds
    @ClassKey(AuthorizationFeatureApi::class)
    @IntoMap
    fun provideAuthorizationFeatureHolder(authorizationFeatureHolder: AuthorizationFeatureHolder): FeatureApiHolder
}