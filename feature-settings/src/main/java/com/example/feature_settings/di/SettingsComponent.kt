package com.example.feature_settings.di

import com.example.common.data.TokenManager
import com.example.common.di.scopes.SettingsScope
import com.example.di.DataApi
import com.example.feature_settings.presentation.SettingsRouter
import com.example.feature_settings.presentation.screen.accounts.AccountsFragment
import com.example.feature_settings.presentation.screen.accounts.AccountsViewModel
import com.example.feature_settings.presentation.screen.singleAccount.SingleAccountFragment
import com.example.feature_settings.presentation.screen.singleAccount.SingleAccountViewModel
import com.example.feature_settings.presentation.screen.categories.CategoriesFragment
import com.example.feature_settings.presentation.screen.categories.CategoriesViewModel
import com.example.feature_settings.presentation.screen.settings.SettingsFragment
import com.example.feature_settings.presentation.screen.settings.SettingsViewModel
import com.example.feature_settings.presentation.screen.singleCategory.SingleCategoryFragment
import com.example.feature_settings.presentation.screen.singleCategory.SingleCategoryViewModel
import com.example.feature_settings.presentation.screen.singleTag.SingleTagFragment
import com.example.feature_settings.presentation.screen.singleTag.SingleTagViewModel
import com.example.feature_settings.presentation.screen.tags.TagsFragment
import com.example.feature_settings.presentation.screen.tags.TagsViewModel
import dagger.BindsInstance
import dagger.Component

@SettingsScope
@Component(
    dependencies = [
        SettingsDependencies::class
    ]
)
interface SettingsComponent : SettingsFeatureApi {

    @Component.Builder
    interface Builder {
        fun build(): SettingsComponent

        fun withDependencies(deps: SettingsDependencies): Builder

        @BindsInstance
        fun router(router: SettingsRouter): Builder

        @BindsInstance
        fun tokenManager(tokenManager: TokenManager): Builder
    }

    @Component(
        dependencies = [
            DataApi::class
        ]
    )
    interface SettingsDependenciesComponent : SettingsDependencies

    fun inject(fragment: SettingsFragment)

    fun settingsViewModel(): SettingsViewModel.Factory

    fun inject(fragment: AccountsFragment)

    fun accountsViewModel(): AccountsViewModel.Factory

    fun inject(fragment: CategoriesFragment)

    fun categoriesViewModel(): CategoriesViewModel.Factory

    fun inject(fragment: TagsFragment)

    fun tagsViewModel(): TagsViewModel.Factory

    fun inject(fragment: SingleAccountFragment)

    fun singleAccountViewModel(): SingleAccountViewModel.Factory

    fun inject(fragment: SingleCategoryFragment)

    fun singleCategoryViewModel(): SingleCategoryViewModel.Factory

    fun inject(fragment: SingleTagFragment)

    fun singleTagViewModel(): SingleTagViewModel.Factory
}