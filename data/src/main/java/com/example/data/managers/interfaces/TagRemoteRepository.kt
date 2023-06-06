package com.example.data.managers.interfaces

import com.example.common.domain.models.Tag
import com.example.common.domain.models.network.BasicResult

interface TagRemoteRepository {

    suspend fun createTagRemote(tag: Tag): BasicResult

    suspend fun updateTagRemote(tag: Tag): BasicResult

    suspend fun deleteTagRemote(tag: Tag): BasicResult
}