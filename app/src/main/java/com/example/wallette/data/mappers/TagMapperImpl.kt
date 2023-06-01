package com.example.wallette.data.mappers

import com.example.common.data.mappers.TagMapper
import com.example.common.data.models.response.TagResponse
import com.example.common.domain.models.Tag
import javax.inject.Inject

class TagMapperImpl @Inject constructor() : TagMapper {

    override fun map(response: TagResponse?): Tag {
        return response?.let {
            Tag(
                id = response.id ?: "",
                name = response.name ?: "",
                color = response.color ?: "#000000"
            )
        } ?: Tag(
            id = "",
            name = "",
            color = "#000000"
        )
    }
}