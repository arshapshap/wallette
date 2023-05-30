package com.example.feature_auth.domain.repositories

import com.example.feature_auth.domain.models.AuthorizationResult

interface AuthorizationRepository {

    suspend fun login(email: String, password: String): AuthorizationResult

    suspend fun register(email: String, password: String): AuthorizationResult
}