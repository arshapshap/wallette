package com.example.feature_auth.di

import com.example.feature_auth.data.repositories.AuthorizationRepositoryImpl
import com.example.feature_auth.domain.repositories.AuthorizationRepository
import dagger.Binds
import dagger.Module

@Module
interface AuthorizationBindsModule {

    @Binds
    fun bindAuthorizationRepository(repositoryImpl: AuthorizationRepositoryImpl): AuthorizationRepository
}