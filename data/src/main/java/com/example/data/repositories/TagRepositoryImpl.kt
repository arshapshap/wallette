package com.example.data.repositories

import com.example.common.data.TokenManager
import com.example.common.domain.models.Tag
import com.example.common.domain.repositories.TagRepository
import com.example.core_db.dao.TagDao
import com.example.data.managers.SyncQueueManager
import com.example.data.managers.enums.RequestType
import com.example.data.mappers.TagMapper
import javax.inject.Inject

class TagRepositoryImpl @Inject constructor(
    private val localSource: TagDao,
    private val tokenManager: TokenManager,
    private val syncQueueManager: SyncQueueManager,
    private val mapper: TagMapper
): TagRepository {

    override suspend fun createTag(tag: Tag) {
        val local = mapper.map(tag)
        val id = localSource.addTag(local)

        if (!tokenManager.isAuthorized()) return

        syncQueueManager.addRequest(RequestType.Create, tag.copy(id = id))
        syncQueueManager.trySynchronize()
    }

    override suspend fun updateTag(tag: Tag) {
        val local = mapper.map(tag)
        localSource.updateTag(local)

        if (!tokenManager.isAuthorized()) return

        syncQueueManager.addRequest(RequestType.Update, tag)
        syncQueueManager.trySynchronize()
    }

    override suspend fun deleteTag(tag: Tag) {
        val local = mapper.map(tag)
        localSource.deleteTag(local)

        if (!tokenManager.isAuthorized()) return

        syncQueueManager.addRequest(RequestType.Delete, tag)
        syncQueueManager.trySynchronize()
    }

    override suspend fun getTags(): List<Tag> {
        val list = localSource.getTags()
        return list.map { mapper.map(it) }
    }
}