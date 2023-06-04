package com.example.common.domain.repositories

import com.example.common.domain.models.Tag

interface TagRepository {

    suspend fun createTag(tag: Tag)

    suspend fun editTag(tag: Tag)

    suspend fun deleteTag(tag: Tag)

    suspend fun getTags(): List<Tag>
}