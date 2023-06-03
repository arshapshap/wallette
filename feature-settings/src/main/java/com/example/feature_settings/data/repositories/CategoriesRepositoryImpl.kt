package com.example.feature_settings.data.repositories

import com.example.common.domain.models.*
import com.example.feature_settings.domain.repositories.CategoriesRepository
import javax.inject.Inject
import kotlin.random.Random

class CategoriesRepositoryImpl @Inject constructor(): CategoriesRepository {

    override suspend fun getCategories(): List<Category> {
        //TODO: УБРАТЬ РАНДОМ
        val list = arrayListOf<Category>()
        val rand = Random(1234)
        for (i in 0..50) {
            list.add(
                Category(
                    id = i.toString(),
                    name = "Категория $i",
                    icon = CategoryIcon.values().filter { it.name != "Empty" }.random(rand),
                    type = TransactionType.values().random(rand)
                )
            )
        }
        return list
    }
}