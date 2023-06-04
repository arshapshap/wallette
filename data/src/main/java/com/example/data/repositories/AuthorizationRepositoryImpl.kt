package com.example.data.repositories

import com.example.common.domain.models.network.AuthorizationResult
import com.example.common.domain.repositories.AuthorizationRepository
import com.example.common.data.TokenManager
import com.example.data.mappers.AuthorizationResponseMapper
import com.example.core_network.data.models.request.LoginRequestModel
import com.example.core_network.data.models.request.RegisterRequestModel
import com.example.core_network.data.services.AuthorizationApiService
import javax.inject.Inject

class AuthorizationRepositoryImpl @Inject constructor(
    private val service: AuthorizationApiService,
    private val mapper: AuthorizationResponseMapper,
    private val tokenManager: TokenManager
) : AuthorizationRepository {

    override suspend fun login(email: String, password: String): AuthorizationResult {
        val requestModel = LoginRequestModel(
            login = email, password = password
        )
        val response = service.authorize(
            model = requestModel
        )
        if (response.isSuccessful == true)
            tokenManager.saveAuthorizationToken(response.token!!)
        return mapper.map(response)
    }

    override suspend fun register(email: String, password: String): AuthorizationResult {
        val requestModel = RegisterRequestModel(
            login = email, password = password
        )
        val response = service.register(
            model = requestModel
        )
        if (response.isSuccessful == true)
            tokenManager.saveAuthorizationToken(response.token!!)
        return mapper.map(response)
    }

    override suspend fun checkIsAuthorized(): Boolean {
        return tokenManager.getAuthorizationToken() != null
    }

    override suspend fun logout() {
        tokenManager.deleteToken()
    }
}