package com.example.feature_settings.domain

import com.example.common.domain.models.Account
import com.example.common.domain.models.Category
import com.example.common.domain.models.Tag
import com.example.common.domain.repositories.AccountRepository
import com.example.common.domain.repositories.AuthorizationRepository
import com.example.common.domain.repositories.CategoryRepository
import com.example.common.domain.repositories.TagRepository
import javax.inject.Inject

class SettingsInteractor @Inject constructor(
    private val accountRepository: AccountRepository,
    private val categoryRepository: CategoryRepository,
    private val tagRepository: TagRepository,
    private val authorizationRepository: AuthorizationRepository
) {
    suspend fun checkIsAuthorized(): Boolean {
        return authorizationRepository.checkIsAuthorized()
    }

    suspend fun logout() {
        return authorizationRepository.logout()
    }

    suspend fun createAccount(account: Account) {
        accountRepository.createAccount(account)
    }

    suspend fun editAccount(account: Account) {
        accountRepository.editAccount(account)
    }

    suspend fun deleteAccount(account: Account) {
        accountRepository.deleteAccount(account)
    }

    suspend fun getAccounts(): List<Account> {
        return accountRepository.getAccounts()
    }

    suspend fun createCategory(category: Category) {
        categoryRepository.createCategory(category)
    }

    suspend fun deleteCategory(category: Category) {
        categoryRepository.deleteCategory(category)
    }

    suspend fun editCategory(category: Category) {
        categoryRepository.editCategory(category)
    }

    suspend fun getCategories(): List<Category> {
        return categoryRepository.getCategories()
    }

    suspend fun createTag(tag: Tag) {
        tagRepository.createTag(tag)
    }

    suspend fun editTag(tag: Tag) {
        tagRepository.editTag(tag)
    }

    suspend fun deleteTag(tag: Tag) {
        tagRepository.deleteTag(tag)
    }

    suspend fun getTags(): List<Tag> {
        return tagRepository.getTags()
    }
}