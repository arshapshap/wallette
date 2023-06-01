package com.example.feature_settings.domain.repositories

import com.example.common.domain.models.Tag

interface TagsRepository {

    suspend fun getTags(): List<Tag>
}