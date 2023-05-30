package com.example.feature_auth.domain

import com.example.common.data.TokenManager
import com.example.feature_auth.domain.models.AuthorizationResult
import com.example.feature_auth.domain.repositories.AuthorizationRepository
import javax.inject.Inject

class AuthorizationInteractor @Inject constructor(
    private val repository: AuthorizationRepository,
    private val tokenManager: TokenManager
) {

    suspend fun login(email: String, password: String): AuthorizationResult {
        return repository.login(email, password)
    }

    suspend fun register(email: String, password: String): AuthorizationResult {
        return repository.register(email, password)
    }

    fun saveToken(token: String) {
        tokenManager.saveAuthorizationToken(token)
    }
}