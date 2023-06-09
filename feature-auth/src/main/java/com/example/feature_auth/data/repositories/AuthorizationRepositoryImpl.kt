package com.example.feature_auth.data.repositories

import com.example.feature_auth.data.mappers.AuthorizationResponseMapper
import com.example.feature_auth.data.network.AuthorizationApiService
import com.example.feature_auth.data.network.request.LoginRequestModel
import com.example.feature_auth.data.network.request.RegisterRequestModel
import com.example.feature_auth.domain.models.AuthorizationResult
import com.example.feature_auth.domain.repositories.AuthorizationRepository
import javax.inject.Inject

class AuthorizationRepositoryImpl @Inject constructor(
    private val service: AuthorizationApiService,
    private val mapper: AuthorizationResponseMapper
) : AuthorizationRepository {

    override suspend fun login(email: String, password: String): AuthorizationResult {
        val requestModel = LoginRequestModel(
            login = email,
            password = password
        )
        val response = service.authorize(
            model = requestModel
        )
        return mapper.map(response)
    }

    override suspend fun register(email: String, password: String): AuthorizationResult {
        val requestModel = RegisterRequestModel(
            login = email,
            password = password
        )
        val response = service.register(
            model = requestModel
        )
        return mapper.map(response)
    }
}