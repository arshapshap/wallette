package com.example.core_db.models

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "TransactionTag",
    primaryKeys = ["transaction_id", "tag_id"]
)
data class TransactionTagCrossRef(
    @ColumnInfo(name = "transaction_id", index = true)
    val transactionId: Long,
    @ColumnInfo(name = "tag_id", index = true)
    val tagId: Long,
    @ColumnInfo(name = "is_synchronized")
    val isSynchronized: Boolean
)
