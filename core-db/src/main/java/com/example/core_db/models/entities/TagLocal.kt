package com.example.core_db.models.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tags")
data class TagLocal(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tag_id")
    val tagId: Long,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "color")
    val color: String,
    @ColumnInfo(name = "is_synchronized")
    val isSynchronized: Boolean,
    @ColumnInfo(name = "must_be_deleted")
    val mustBeDeleted: Boolean
)
