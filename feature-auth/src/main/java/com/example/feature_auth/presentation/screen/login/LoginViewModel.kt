package com.example.feature_auth.presentation.screen.login

import com.example.feature_auth.R
import com.example.feature_auth.domain.AuthorizationInteractor
import com.example.feature_auth.presentation.screen.AuthorizationRouter
import com.example.feature_auth.presentation.base.AuthorizationViewModel
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class LoginViewModel @AssistedInject constructor(
    private val interactor: AuthorizationInteractor,
    private val router: AuthorizationRouter
) : AuthorizationViewModel(
    interactor = interactor,
    router = router
) {

    fun openRegisterPage() {
        router.openRegisterPage()
    }

    fun tryLogin(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _errorFromResourceLiveData.postValue(R.string.empty_fields)
            return
        }
        tryAuthorize {
            val result = interactor.login(
                email = email,
                password = password
            )
            handleServerResult(result) { errorMessage ->
                when (errorMessage) {
                    "Incorrect login or password" -> _errorFromResourceLiveData.postValue(R.string.incorrect_login_or_password)
                    "Such a user doesn't exist" -> _errorFromResourceLiveData.postValue(R.string.not_existent_user)
                    else -> _errorFromResourceLiveData.postValue(R.string.error)
                }
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create() : LoginViewModel
    }
}