package com.example.core_db.dao

import androidx.room.*
import com.example.core_db.models.entities.TagLocal

@Dao
abstract class TagDao {

    @Insert
    abstract suspend fun addTag(tagLocal: TagLocal)

    @Update
    abstract suspend fun updateTag(tagLocal: TagLocal)

    @Delete
    abstract suspend fun deleteTag(tagLocal: TagLocal)

    @Query("SELECT * FROM Tags")
    abstract suspend fun getTags(): List<TagLocal>
}