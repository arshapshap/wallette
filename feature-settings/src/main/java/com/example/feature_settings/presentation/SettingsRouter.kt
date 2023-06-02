package com.example.feature_settings.presentation

import com.example.common.domain.models.Category
import com.example.common.domain.models.Tag

interface SettingsRouter {

    fun openAccounts()

    fun openCategories()

    fun openTags()

    fun openSingleAccount(account: com.example.common.domain.models.Account)

    fun openSingleCategory(category: Category)

    fun openSingleTag(tag: Tag)

    fun openAccountCreating()

    fun openCategoryCreating()

    fun openTagCreating()

    fun openTransferCreating()
}