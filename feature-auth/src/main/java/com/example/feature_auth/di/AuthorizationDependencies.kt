package com.example.feature_auth.di

import com.example.common.domain.repositories.AuthorizationRepository

interface AuthorizationDependencies {

    fun authorizationRepository(): AuthorizationRepository
}