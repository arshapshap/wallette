package com.example.feature_settings.di

import com.example.feature_settings.data.repositories.AccountsRepositoryImpl
import com.example.feature_settings.data.repositories.CategoriesRepositoryImpl
import com.example.feature_settings.data.repositories.TagsRepositoryImpl
import com.example.feature_settings.domain.repositories.AccountsRepository
import com.example.feature_settings.domain.repositories.CategoriesRepository
import com.example.feature_settings.domain.repositories.TagsRepository
import dagger.Binds
import dagger.Module

@Module
interface SettingsBindsModule {

    @Binds
    fun bindAccountsRepository(repositoryImpl: AccountsRepositoryImpl): AccountsRepository

    @Binds
    fun bindCategoriesRepository(repositoryImpl: CategoriesRepositoryImpl): CategoriesRepository

    @Binds
    fun bindTagsRepository(repositoryImpl: TagsRepositoryImpl): TagsRepository
}