package com.example.data.mappers

import com.example.common.domain.models.network.AuthorizationResult
import com.example.core_network.data.models.response.AuthorizationResponse
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