package com.example.common.domain.repositories

import com.example.common.domain.models.network.AuthorizationResult

interface AuthorizationRepository {

    suspend fun login(email: String, password: String): AuthorizationResult

    suspend fun register(email: String, password: String): AuthorizationResult

    suspend fun checkIsAuthorized(): Boolean

    suspend fun logout()
}