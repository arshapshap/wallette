package com.example.feature_settings.di

import com.example.common.di.scopes.SettingsScope
import com.example.feature_settings.presentation.SettingsRouter
import com.example.feature_settings.presentation.screen.accounts.AccountsFragment
import com.example.feature_settings.presentation.screen.accounts.AccountsViewModel
import com.example.feature_settings.presentation.screen.categories.CategoriesFragment
import com.example.feature_settings.presentation.screen.categories.CategoriesViewModel
import com.example.feature_settings.presentation.screen.settings.SettingsFragment
import com.example.feature_settings.presentation.screen.settings.SettingsViewModel
import com.example.feature_settings.presentation.screen.tags.TagsFragment
import com.example.feature_settings.presentation.screen.tags.TagsViewModel
import dagger.BindsInstance
import dagger.Component

@SettingsScope
@Component(
    modules = [SettingsBindsModule::class]
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

    fun inject(fragment: AccountsFragment)

    fun accountsViewModel(): AccountsViewModel.Factory

    fun inject(fragment: CategoriesFragment)

    fun categoriesViewModel(): CategoriesViewModel.Factory

    fun inject(fragment: TagsFragment)

    fun tagsViewModel(): TagsViewModel.Factory
}