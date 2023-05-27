package com.example.feature_auth.domain

import com.example.feature_auth.domain.models.AuthorizationResult
import javax.inject.Inject

class AuthorizationInteractor @Inject constructor() {

    suspend fun login(email: String, password: String): AuthorizationResult {
        return AuthorizationResult(
            isSuccessful = false,
            errorMessage = "Неверный логин или пароль"
        )
    }

    suspend fun register(email: String, password: String): AuthorizationResult {
        return AuthorizationResult(
            isSuccessful = true,
            token = "asddsads"
        )
    }
}