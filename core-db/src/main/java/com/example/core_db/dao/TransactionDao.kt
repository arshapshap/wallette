package com.example.core_db.dao

import androidx.room.*
import com.example.core_db.models.TransactionWithTagsLocal
import com.example.core_db.models.entities.TransactionLocal

@Dao
abstract class TransactionDao {

    @Insert
    abstract fun addTransaction(transactionLocal: TransactionLocal)

    @Update
    abstract fun updateTransaction(transactionLocal: TransactionLocal)

    @Delete
    abstract fun deleteTransaction(transactionLocal: TransactionLocal)

    @Transaction
    @Query("SELECT * FROM Transactions")
    abstract fun getTransactionsWithTags(): List<TransactionWithTagsLocal>
}