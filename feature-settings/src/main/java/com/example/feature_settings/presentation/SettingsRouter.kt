package com.example.feature_settings.presentation

import com.example.common.domain.models.Account
import com.example.common.domain.models.Category
import com.example.common.domain.models.Tag

interface SettingsRouter {

    fun openAccounts()

    fun openCategories()

    fun openTags()

    fun openSingleAccount(account: Account? = null)

    fun openSingleCategory(category: Category? = null)

    fun openSingleTag(tag: Tag? = null)

    fun openTransferCreating()

    fun openLoginPage()
}