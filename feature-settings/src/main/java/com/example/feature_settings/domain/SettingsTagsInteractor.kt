package com.example.feature_settings.domain

import com.example.common.domain.models.Tag
import com.example.common.domain.repositories.TagRepository
import javax.inject.Inject

class SettingsTagsInteractor @Inject constructor(
    private val tagRepository: TagRepository
) {

    suspend fun createTag(tag: Tag) {
        tagRepository.createTag(tag)
    }

    suspend fun editTag(tag: Tag) {
        tagRepository.updateTag(tag)
    }

    suspend fun deleteTag(tag: Tag) {
        tagRepository.deleteTag(tag)
    }

    suspend fun getTags(): List<Tag> {
        return tagRepository.getTags()
    }}