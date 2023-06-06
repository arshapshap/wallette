package com.example.core_db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SyncRequests")
data class SyncRequest(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "request_id")
    val id: Long,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "entity_id")
    val entityId: Long,
    @ColumnInfo(name = "entity")
    val entity: String
)
