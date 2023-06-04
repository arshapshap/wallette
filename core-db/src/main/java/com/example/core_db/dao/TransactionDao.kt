package com.example.core_db.dao

import androidx.room.*
import com.example.core_db.models.FullTransactionLocal
import com.example.core_db.models.entities.TransactionLocal

@Dao
abstract class TransactionDao {

    @Insert
    abstract suspend fun addTransaction(transactionLocal: TransactionLocal)

    @Update
    abstract suspend fun updateTransaction(transactionLocal: TransactionLocal)

    @Delete
    abstract suspend fun deleteTransaction(transactionLocal: TransactionLocal)

    @Transaction
    @Query("SELECT * FROM Transactions")
    abstract suspend fun getTransactions(): List<FullTransactionLocal>
}