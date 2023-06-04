package com.example.core_db.dao

import androidx.room.*
import com.example.core_db.models.entities.CategoryLocal

@Dao
abstract class CategoryDao {

    @Insert
    abstract fun addCategory(categoryLocal: CategoryLocal)

    @Update
    abstract fun updateCategory(categoryLocal: CategoryLocal)

    @Delete
    abstract fun deleteCategory(categoryLocal: CategoryLocal)

    @Query("SELECT * FROM Categories")
    abstract fun getCategories(): List<CategoryLocal>
}