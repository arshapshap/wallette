package com.example.feature_settings.presentation

import com.example.common.domain.models.Category
import com.example.common.domain.models.Tag

interface SettingsRouter {

    fun openAccounts()

    fun openCategories()

    fun openTags()

    fun openSingleAccount(account: com.example.common.domain.models.Account)

    fun openCategory(category: Category)

    fun openTag(tag: Tag)

    fun openAccountCreating()

    fun openCategoryCreating()

    fun openTagCreating()

    fun openTransferCreating()
}