package com.example.feature_settings.di

import com.example.common.domain.repositories.AccountRepository
import com.example.common.domain.repositories.CategoryRepository
import com.example.common.domain.repositories.TagRepository

interface SettingsDependencies {

    fun accountsRepository(): AccountRepository

    fun categoriesRepository(): CategoryRepository

    fun tagsRepository(): TagRepository
}