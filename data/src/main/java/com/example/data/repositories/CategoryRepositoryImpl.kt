package com.example.data.repositories

import com.example.common.domain.models.*
import com.example.common.domain.repositories.CategoryRepository
import com.example.core_db.dao.CategoryDao
import com.example.data.mappers.CategoryMapper
import javax.inject.Inject
import kotlin.random.Random

class CategoryRepositoryImpl @Inject constructor(
    private val localSource: CategoryDao,
    private val mapper: CategoryMapper
): CategoryRepository {

    override suspend fun createCategory(category: Category) {
        val local = mapper.map(category)
        localSource.addCategory(local)
    }

    override suspend fun editCategory(category: Category) {
        val local = mapper.map(category)
        localSource.updateCategory(local)
    }

    override suspend fun deleteCategory(category: Category) {
        val local = mapper.map(category)
        localSource.deleteCategory(local)
    }

    override suspend fun getCategories(): List<Category> {
        val list = localSource.getCategories()
        return list.map { mapper.map(it) }
    }

    private fun getRandomCategories(): List<Category> {
        val list = arrayListOf<Category>()
        val rand = Random(1234)
        for (i in 0..50) {
            list.add(
                Category(
                    id = i.toLong(),
                    name = "Категория $i",
                    icon = CategoryIcon.values().filter { it.name != "Empty" }.random(rand),
                    type = TransactionType.values().random(rand)
                )
            )
        }
        return list
    }
}