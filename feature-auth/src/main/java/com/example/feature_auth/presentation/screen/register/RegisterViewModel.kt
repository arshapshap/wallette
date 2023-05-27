package com.example.feature_auth.presentation.screen.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.common.base.BaseViewModel
import com.example.feature_auth.domain.AuthorizationInteractor
import com.example.feature_auth.presentation.screen.AuthorizationRouter
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class RegisterViewModel @AssistedInject constructor(
    private val interactor: AuthorizationInteractor,
    private val router: AuthorizationRouter
) : BaseViewModel() {

    private val _errorMessageLiveData = MutableLiveData<String>()
    val errorMessageLiveData: LiveData<String>
        get() = _errorMessageLiveData

    fun openLoginPage() {
        router.openLoginPage()
    }

    fun tryRegister(email: String, password: String, passwordConfirm: String) {
        if (password != passwordConfirm) {
            _errorMessageLiveData.postValue("Пароли должны совпадать")
            return
        }
        if (email.isEmpty() || password.isEmpty()) {
            _errorMessageLiveData.postValue("Все поля должны быть заполнены")
            return
        }
        viewModelScope.launch {
            kotlin.runCatching {
                val result = interactor.register(
                    email = email,
                    password = password
                )
                if (result.isSuccessful)
                    router.openStatistics()
                else {
                    _errorMessageLiveData.postValue(result.errorMessage)
                }
            }.onFailure {
                _errorMessageLiveData.postValue("Произошла ошибка")
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create() : RegisterViewModel
    }
}