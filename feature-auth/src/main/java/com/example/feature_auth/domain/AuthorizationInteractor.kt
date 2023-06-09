package com.example.feature_auth.domain

import com.example.common.domain.models.network.AuthorizationResult
import com.example.common.domain.repositories.AuthorizationRepository
import javax.inject.Inject

class AuthorizationInteractor @Inject constructor(
    private val repository: AuthorizationRepository
) {

    suspend fun login(email: String, password: String): AuthorizationResult {
        return repository.login(email, password)
    }

    suspend fun register(email: String, password: String): AuthorizationResult {
        return repository.register(email, password)
    }
}