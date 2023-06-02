package com.example.feature_settings.data.repositories

import com.example.common.domain.models.Tag
import com.example.feature_settings.domain.repositories.TagsRepository
import javax.inject.Inject
import kotlin.random.Random

class TagsRepositoryImpl @Inject constructor(): TagsRepository {

    override suspend fun getTags(): List<Tag> {
        //TODO: УБРАТЬ РАНДОМ
        val list = arrayListOf<Tag>()
        val rand = Random(1234)
        for (i in 0..10) {
            list.add(
                Tag(
                    id = i.toString(),
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