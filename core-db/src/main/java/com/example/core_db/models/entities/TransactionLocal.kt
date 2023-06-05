package com.example.core_db.models.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Transactions",
    foreignKeys = [
        ForeignKey(
            entity = AccountLocal::class,
            parentColumns = arrayOf("account_id"),
            childColumns = arrayOf("account_id"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = AccountLocal::class,
            parentColumns = arrayOf("account_id"),
            childColumns = arrayOf("account_destination_id"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CategoryLocal::class,
            parentColumns = arrayOf("category_id"),
            childColumns = arrayOf("category_id"),
            onUpdate = ForeignKey.SET_NULL,
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class TransactionLocal (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "transaction_id")
    val transactionId: Long,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "date_in_days")
    val dateInDays: Long,
    @ColumnInfo(name = "amount")
    val amount: Double,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "account_id", index = true)
    val accountId: Long,
    @ColumnInfo(name = "account_destination_id", index = true)
    val accountDestinationId: Long?,
    @ColumnInfo(name = "category_id", index = true)
    val categoryId: Long?
)