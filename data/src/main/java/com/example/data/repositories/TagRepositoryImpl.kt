package com.example.data.repositories

import com.example.common.domain.models.Tag
import com.example.common.domain.repositories.TagRepository
import com.example.core_db.dao.TagDao
import com.example.data.mappers.TagMapper
import javax.inject.Inject

class TagRepositoryImpl @Inject constructor(
    private val localSource: TagDao,
    private val mapper: TagMapper
): TagRepository {

    override suspend fun createTag(tag: Tag) {
        val local = mapper.map(tag)
        localSource.addTag(local)
    }

    override suspend fun editTag(tag: Tag) {
        val local = mapper.map(tag)
        localSource.updateTag(local)
    }

    override suspend fun deleteTag(tag: Tag) {
        val local = mapper.map(tag)
        localSource.deleteTag(local)
    }

    override suspend fun getTags(): List<Tag> {
        val list = localSource.getTags()
        return list.map { mapper.map(it) }
    }
}