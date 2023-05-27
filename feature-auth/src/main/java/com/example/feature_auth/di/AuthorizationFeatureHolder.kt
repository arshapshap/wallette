package com.example.feature_statistics_impl.di

import com.example.common.di.FeatureApiHolder
import com.example.common.di.FeatureContainer
import com.example.common.di.scopes.ApplicationScope
import com.example.feature_auth.di.DaggerAuthorizationComponent
import com.example.feature_auth.presentation.screen.AuthorizationRouter
import javax.inject.Inject

@ApplicationScope
class AuthorizationFeatureHolder @Inject constructor(
    featureContainer: FeatureContainer,
    private val authorizationRouter: AuthorizationRouter
) : FeatureApiHolder(featureContainer) {

    override fun initializeDependencies(): Any {
        return DaggerAuthorizationComponent.builder()
            .router(authorizationRouter)
            .build()
    }
}