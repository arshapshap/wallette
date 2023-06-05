package com.example.data.repositories

import com.example.common.data.TokenManager
import com.example.common.domain.models.Tag
import com.example.common.domain.models.network.BasicResult
import com.example.common.domain.repositories.TagRepository
import com.example.core_db.dao.TagDao
import com.example.core_network.data.services.TagsApiService
import com.example.data.mappers.BasicResultMapper
import com.example.data.mappers.TagMapper
import javax.inject.Inject

class TagRepositoryImpl @Inject constructor(
    private val localSource: TagDao,
    private val remoteSource: TagsApiService,
    private val tokenManager: TokenManager,
    private val mapper: TagMapper,
    private val resultMapper: BasicResultMapper
): TagRepository {

    override suspend fun createTag(tag: Tag) {
        val local = mapper.map(tag)
        val id = localSource.addTag(local)

        if (!tokenManager.isAuthorized()) return

        val result = createTagRemote(tag.copy(id = id))
        if (result.isSuccessful)
            setSynchronized(tag)
    }

    override suspend fun updateTag(tag: Tag) {
        val local = mapper.map(tag)
        localSource.updateTag(local)

        if (!tokenManager.isAuthorized()) return

        val result = updateTagRemote(tag)
        if (result.isSuccessful)
            setSynchronized(tag)
    }

    override suspend fun deleteTag(tag: Tag) {
        val local = mapper.map(tag)
        setMustBeDeleted(tag)

        if (!tokenManager.isAuthorized()) {
            localSource.deleteTag(local)
            return
        }

        val result = deleteTagRemote(tag.id)
        if (result.isSuccessful)
            localSource.deleteTag(local)
    }

    override suspend fun getTags(): List<Tag> {
        val list = localSource.getTags()
        return list.map { mapper.map(it) }
    }

    private suspend fun createTagRemote(tag: Tag): BasicResult {
        val model = mapper.mapToCreatingModel(tag)
        val response = remoteSource.createTag(model)
        return resultMapper.map(response)
    }

    private suspend fun updateTagRemote(tag: Tag): BasicResult {
        val model = mapper.mapToEditingModel(tag)
        val response = remoteSource.updateTag(model)
        return resultMapper.map(response)
    }

    private suspend fun deleteTagRemote(id: Long): BasicResult {
        val response = remoteSource.deleteTagById(id)
        return resultMapper.map(response)
    }

    private suspend fun setSynchronized(tag: Tag) {
        val local = mapper.map(tag)
        localSource.updateTag(
            local.copy(isSynchronized = true)
        )
    }

    private suspend fun setMustBeDeleted(tag: Tag) {
        val local = mapper.map(tag)
        localSource.updateTag(
            local.copy(mustBeDeleted = true)
        )
    }
}