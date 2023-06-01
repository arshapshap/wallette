package com.example.common.data.mappers

import com.example.common.data.models.response.TagResponse
import com.example.common.domain.models.Tag

interface TagMapper {

    fun map(response: TagResponse?): Tag
}