package com.example.feature_settings.domain

import com.example.common.domain.models.Account
import com.example.common.domain.models.Category
import com.example.common.domain.models.Tag
import com.example.common.domain.repositories.AccountRepository
import com.example.common.domain.repositories.CategoryRepository
import com.example.common.domain.repositories.TagRepository
import javax.inject.Inject

class SettingsInteractor @Inject constructor(
    private val accountRepository: AccountRepository,
    private val categoryRepository: CategoryRepository,
    private val tagRepository: TagRepository
) {

    suspend fun getAccounts(): List<Account> {
        return accountRepository.getAccounts()
    }

    suspend fun getCategories(): List<Category> {
        return categoryRepository.getCategories()
    }

    suspend fun getTags(): List<Tag> {
        return tagRepository.getTags()
    }

    suspend fun createAccount(account: Account) {

    }

    suspend fun createCategory(category: Category) {

    }

    suspend fun createTag(tag: Tag) {

    }

    suspend fun editAccount(account: Account) {

    }

    suspend fun editCategory(category: Category) {

    }

    suspend fun editTag(tag: Tag) {

    }
}