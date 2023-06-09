package com.example.common.data

interface TokenManager {

    fun getAuthorizationToken(): String?

    fun saveAuthorizationToken(token: String)

    fun deleteToken()
}