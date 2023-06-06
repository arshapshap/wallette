package com.example.feature_auth.di

import com.example.common.di.FeatureApiHolder
import com.example.common.di.FeatureContainer
import com.example.common.di.scopes.ApplicationScope
import com.example.di.DataApi
import com.example.feature_auth.presentation.screen.AuthorizationRouter
import javax.inject.Inject

@ApplicationScope
class AuthorizationFeatureHolder @Inject constructor(
    featureContainer: FeatureContainer,
    private val router: AuthorizationRouter,
) : FeatureApiHolder(featureContainer) {

    override fun initializeDependencies(): Any {
        val authorizationDependencies = DaggerAuthorizationComponent_AuthorizationDependenciesComponent.builder()
            .dataApi(getFeature(DataApi::class.java))
            .build()
        return DaggerAuthorizationComponent.builder()
            .router(router)
            .withDependencies(authorizationDependencies)
            .build()
    }
}