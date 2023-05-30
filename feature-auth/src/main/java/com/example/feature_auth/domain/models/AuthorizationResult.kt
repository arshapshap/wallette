package com.example.feature_auth.domain.models

data class AuthorizationResult(
    val isSuccessful: Boolean,
    val token: String = "",
    val errorMessage: String = ""
)
