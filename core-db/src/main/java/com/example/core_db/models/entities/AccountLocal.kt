package com.example.core_db.models.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Accounts")
data class AccountLocal(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "account_id")
    val accountId: Long,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "icon")
    val icon: String,
    @ColumnInfo(name = "current_balance")
    val currentBalance: Double,
    @ColumnInfo(name = "start_balance")
    val startBalance: Double,
    @ColumnInfo(name = "currency")
    val currency: String,
    @ColumnInfo(name = "is_synchronized")
    val isSynchronized: Boolean,
    @ColumnInfo(name = "must_be_deleted")
    val mustBeDeleted: Boolean
)