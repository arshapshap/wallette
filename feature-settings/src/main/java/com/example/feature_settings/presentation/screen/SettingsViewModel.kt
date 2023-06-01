package com.example.feature_settings.presentation.screen

import com.example.common.presentation.base.BaseViewModel
import com.example.feature_settings.presentation.SettingsRouter
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class SettingsViewModel @AssistedInject constructor(
    private val router: SettingsRouter
) : BaseViewModel() {

    @AssistedFactory
    interface Factory {

        fun create(): SettingsViewModel
    }
}