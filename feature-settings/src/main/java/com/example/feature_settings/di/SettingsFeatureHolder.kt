package com.example.feature_settings.di

import com.example.common.data.TokenManager
import com.example.common.di.FeatureApiHolder
import com.example.common.di.FeatureContainer
import com.example.common.di.scopes.ApplicationScope
import com.example.feature_settings.presentation.SettingsRouter
import javax.inject.Inject

@ApplicationScope
class SettingsFeatureHolder @Inject constructor(
    featureContainer: FeatureContainer,
    private val router: SettingsRouter,
    private val tokenManager: TokenManager
) : FeatureApiHolder(featureContainer) {

    override fun initializeDependencies(): Any {
        return DaggerSettingsComponent.builder()
            .router(router)
            .tokenManager(tokenManager)
            .build()
    }
}