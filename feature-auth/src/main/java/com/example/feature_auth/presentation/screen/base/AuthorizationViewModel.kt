package com.example.feature_auth.presentation.screen.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.common.presentation.base.BaseViewModel
import com.example.feature_auth.R
import com.example.feature_auth.domain.AuthorizationInteractor
import com.example.feature_auth.domain.models.AuthorizationResult
import com.example.feature_auth.presentation.screen.AuthorizationRouter
import kotlinx.coroutines.launch
import java.net.UnknownHostException

abstract class AuthorizationViewModel(
    private val interactor: AuthorizationInteractor,
    private val router: AuthorizationRouter
) : BaseViewModel() {

    protected val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String>
        get() = _errorLiveData

    protected val _errorFromResourceLiveData = MutableLiveData<Int>()
    val errorFromResourceLiveData: LiveData<Int>
        get() = _errorFromResourceLiveData

    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean>
        get() = _loadingLiveData

    protected fun handleServerResult(result: AuthorizationResult, exceptionHandler: (String) -> Unit) {
        if (result.isSuccessful) {
            interactor.saveToken(result.token)
            router.openStatistics()
        }
        else
            exceptionHandler.invoke(result.errorMessage)
    }

    protected fun tryAuthorize(request: suspend () -> Unit) {
        _loadingLiveData.postValue(true)
        viewModelScope.launch {
            kotlin.runCatching {
                request.invoke()
            }.onFailure(::handleNetworkException)
                .also { _loadingLiveData.postValue(false) }
        }
    }

    private fun handleNetworkException(error: Throwable) {
        when (error) {
            is UnknownHostException -> _errorFromResourceLiveData.postValue(R.string.network_unavailable)
            else -> _errorFromResourceLiveData.postValue(R.string.error)
        }
    }
}