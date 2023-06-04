package com.example.data.repositories

import com.example.common.domain.models.Tag
import com.example.common.domain.repositories.TagRepository
import com.example.core_db.dao.TagDao
import com.example.data.mappers.TagMapper
import javax.inject.Inject
import kotlin.random.Random

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

    private fun getRandomTags(): List<Tag> {
        val list = arrayListOf<Tag>()
        val rand = Random(1234)
        for (i in 0..10) {
            list.add(
                Tag(
                    id = i.toLong(),
                    name = "Метка $i",
                    color = getRandomColor(rand)
                )
            )
        }
        return list
    }

    private fun getRandomColor(rand: Random): String {
        val r = rand.nextInt(256).toString(16).padStart(2, '0')
        val g = rand.nextInt(256).toString(16).padStart(2, '0')
        val b = rand.nextInt(256).toString(16).padStart(2, '0')
        return "#$r$g$b"
    }
}