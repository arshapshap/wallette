package com.example.wallette.di.app

import com.example.common.di.FeatureApiHolder
import com.example.common.di.scopes.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class FeatureManagerModule {

    @ApplicationScope
    @Provides
    fun provideFeatureHolderManager(featureApiHolderMap: @JvmSuppressWildcards Map<Class<*>, FeatureApiHolder>): FeatureHolderManager {
        return FeatureHolderManager(featureApiHolderMap)
    }
}