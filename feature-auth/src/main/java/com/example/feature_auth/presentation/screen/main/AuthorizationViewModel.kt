package com.example.feature_auth.presentation.screen.main

import com.example.common.base.BaseViewModel
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class AuthorizationViewModel @AssistedInject constructor() : BaseViewModel() {

    @AssistedFactory
    interface Factory {
        fun create() : AuthorizationViewModel
    }
}