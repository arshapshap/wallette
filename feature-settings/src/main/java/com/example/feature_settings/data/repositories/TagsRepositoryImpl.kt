package com.example.feature_settings.data.repositories

import com.example.common.domain.models.Tag
import com.example.feature_settings.domain.repositories.TagsRepository
import javax.inject.Inject

class TagsRepositoryImpl @Inject constructor(): TagsRepository {

    override suspend fun getTags(): List<Tag> {
        val tagRed = Tag(
            id = "tagRed",
            name = "Красный",
            color = "#FF0000"
        )
        val tagBlue = Tag(
            id = "tagBlue",
            name = "Синий",
            color = "#0000FF"
        )
        val tagGreen = Tag(
            id = "tagGreen",
            name = "Зелёный",
            color = "#00FF00"
        )
        return listOf(tagRed, tagBlue, tagGreen, tagRed, tagBlue, tagGreen, tagRed, tagBlue, tagGreen, tagRed, tagBlue, tagGreen)
    }
}