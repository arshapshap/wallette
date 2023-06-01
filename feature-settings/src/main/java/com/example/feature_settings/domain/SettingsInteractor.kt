package com.example.feature_settings.domain

import com.example.common.domain.models.Account
import com.example.common.domain.models.Category
import com.example.common.domain.models.Tag
import com.example.feature_settings.domain.repositories.AccountsRepository
import com.example.feature_settings.domain.repositories.CategoriesRepository
import com.example.feature_settings.domain.repositories.TagsRepository
import javax.inject.Inject

class SettingsInteractor @Inject constructor(
    private val accountsRepository: AccountsRepository,
    private val categoriesRepository: CategoriesRepository,
    private val tagsRepository: TagsRepository
) {

    suspend fun getAccounts(): List<Account> {
        return accountsRepository.getAccounts()
    }

    suspend fun getCategories(): List<Category> {
        return categoriesRepository.getCategories()
    }

    suspend fun getTags(): List<Tag> {
        return tagsRepository.getTags()
    }
}