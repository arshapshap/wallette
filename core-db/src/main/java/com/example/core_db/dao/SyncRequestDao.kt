package com.example.core_db.dao

import androidx.room.*
import com.example.core_db.models.SyncRequest

@Dao
abstract class SyncRequestDao {

    @Insert
    abstract suspend fun addRequest(syncRequestDao: SyncRequestDao): Long

    @Delete
    abstract suspend fun deleteRequest(syncRequest: SyncRequest)

    @Query("SELECT * FROM SyncRequests")
    abstract suspend fun getRequests(): List<SyncRequest>
}