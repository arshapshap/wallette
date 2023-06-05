package com.example.core_db.dao

import androidx.room.*
import com.example.core_db.models.entities.CategoryLocal

@Dao
abstract class CategoryDao {

    @Insert
    abstract suspend fun addCategory(categoryLocal: CategoryLocal): Long

    @Update
    abstract suspend fun updateCategory(categoryLocal: CategoryLocal)

    @Delete
    abstract suspend fun deleteCategory(categoryLocal: CategoryLocal)

    @Query("SELECT * FROM Categories")
    abstract suspend fun getCategories(): List<CategoryLocal>
}