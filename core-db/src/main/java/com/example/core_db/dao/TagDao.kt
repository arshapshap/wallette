package com.example.core_db.dao

import androidx.room.*
import com.example.core_db.models.entities.TagLocal

@Dao
abstract class TagDao {

    @Insert
    abstract fun addTag(tagLocal: TagLocal)

    @Update
    abstract fun updateTag(tagLocal: TagLocal)

    @Delete
    abstract fun deleteTag(tagLocal: TagLocal)

    @Query("SELECT * FROM Tags")
    abstract fun getTags(): List<TagLocal>
}