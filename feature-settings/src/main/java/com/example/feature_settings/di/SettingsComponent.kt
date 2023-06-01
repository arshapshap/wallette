package com.example.feature_settings.di

import com.example.common.di.scopes.SettingsScope
import com.example.feature_settings.presentation.SettingsRouter
import com.example.feature_settings.presentation.screen.SettingsFragment
import com.example.feature_settings.presentation.screen.SettingsViewModel
import dagger.BindsInstance
import dagger.Component

@SettingsScope
@Component(
    modules = []
)
interface SettingsComponent : SettingsFeatureApi {

    @Component.Builder
    interface Builder {
        fun build(): SettingsComponent

        @BindsInstance
        fun router(router: SettingsRouter): Builder
    }

    fun inject(fragment: SettingsFragment)

    fun settingsViewModel(): SettingsViewModel.Factory
}