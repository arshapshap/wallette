package com.example.feature_auth.data.mappers

import com.example.feature_auth.data.network.response.AuthorizationResponse
import com.example.feature_auth.domain.models.AuthorizationResult
import javax.inject.Inject

class AuthorizationResponseMapper @Inject constructor() {

    fun map(response: AuthorizationResponse): AuthorizationResult {
        return with(response) {
            AuthorizationResult(
                isSuccessful = isSuccessful ?: false,
                token = token ?: "",
                errorMessage = errorMessage ?: ""
            )
        }
    }
}