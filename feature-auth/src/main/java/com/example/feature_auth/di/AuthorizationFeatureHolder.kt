package com.example.feature_statistics_impl.di

import com.example.common.data.TokenManager
import com.example.common.di.FeatureApiHolder
import com.example.common.di.FeatureContainer
import com.example.common.di.scopes.ApplicationScope
import com.example.feature_auth.di.DaggerAuthorizationComponent
import com.example.feature_auth.presentation.screen.AuthorizationRouter
import retrofit2.Retrofit
import javax.inject.Inject

@ApplicationScope
class AuthorizationFeatureHolder @Inject constructor(
    featureContainer: FeatureContainer,
    private val authorizationRouter: AuthorizationRouter,
    private val retrofit: Retrofit,
    private val tokenManager: TokenManager
) : FeatureApiHolder(featureContainer) {

    override fun initializeDependencies(): Any {
        return DaggerAuthorizationComponent.builder()
            .router(authorizationRouter)
            .retrofit(retrofit)
            .tokenManager(tokenManager)
            .build()
    }
}