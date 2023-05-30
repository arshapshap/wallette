package com.example.feature_auth.presentation.screen.register

import com.example.feature_auth.R
import com.example.feature_auth.domain.AuthorizationInteractor
import com.example.feature_auth.presentation.screen.AuthorizationRouter
import com.example.feature_auth.presentation.screen.base.AuthorizationViewModel
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
                    "Login is taken" -> _errorFromResourceLiveData.postValue(R.string.email_is_taken)
                    "Password is too short" -> _errorFromResourceLiveData.postValue(R.string.too_short_password)
                    else -> _errorLiveData.postValue(result.errorMessage)
                }
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create() : RegisterViewModel
    }
}