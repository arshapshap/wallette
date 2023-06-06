package com.example.feature_auth.presentation.screen.register

import com.example.feature_auth.R
import com.example.feature_auth.domain.AuthorizationInteractor
import com.example.feature_auth.presentation.screen.AuthorizationRouter
import com.example.feature_auth.presentation.base.AuthorizationViewModel
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class RegisterViewModel @AssistedInject constructor(
    private val interactor: AuthorizationInteractor,
    private val router: AuthorizationRouter
) : AuthorizationViewModel(
    interactor = interactor,
    router = router
) {

    fun openLoginPage() {
        router.openLoginPage()
    }

    fun tryRegister(email: String, password: String, passwordConfirm: String) {
        if (password != passwordConfirm) {
            _errorFromResourceLiveData.postValue(R.string.passwords_must_match)
            return
        }
        if (email.isEmpty() || password.isEmpty()) {
            _errorFromResourceLiveData.postValue(R.string.empty_fields)
            return
        }
        tryAuthorize {
            val result = interactor.register(
                email = email,
                password = password
            )
            handleServerResult(result) { errorMessage ->
                when (errorMessage) {
                    "Username '$email' is already taken." -> _errorFromResourceLiveData.postValue(R.string.email_is_taken)
                    "Passwords must be at least 8 characters." -> _errorFromResourceLiveData.postValue(R.string.too_short_password)
                    "Wrong email format" -> _errorFromResourceLiveData.postValue(R.string.wrong_email_format)
                    else -> _errorFromResourceLiveData.postValue(R.string.error)
                }
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create() : RegisterViewModel
    }
}