package com.example.wallette.data.sharedPreferences

import android.content.SharedPreferences
import com.example.common.data.TokenManager
import javax.inject.Inject

class SharedPreferencesManager @Inject constructor(
    private val sharedPrefs: SharedPreferences
) : TokenManager {

    override fun getAuthorizationToken(): String?
        = sharedPrefs.getString(TOKEN_KEY, null)

    override fun saveAuthorizationToken(token: String)
        = sharedPrefs.edit().putString(TOKEN_KEY, token).apply()

    override fun deleteToken()
        = sharedPrefs.edit().putString(TOKEN_KEY, null).apply()

    companion object {
        private const val TOKEN_KEY = "token"
    }
}