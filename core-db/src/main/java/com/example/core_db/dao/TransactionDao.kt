package com.example.core_db.dao

import androidx.room.*
import com.example.core_db.models.FullTransactionLocal
import com.example.core_db.models.TransactionTagCrossRef
import com.example.core_db.models.entities.TransactionLocal

@Dao
abstract class TransactionDao {

    @Insert
    abstract suspend fun addTransaction(transactionLocal: TransactionLocal)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun addTransactionTag(transactionTagCrossRef: TransactionTagCrossRef)

    @Update
    abstract suspend fun updateTransaction(transactionLocal: TransactionLocal)

    @Delete
    abstract suspend fun deleteTransaction(transactionLocal: TransactionLocal)

    @Delete
    abstract suspend fun deleteTransactionTag(transactionTagCrossRef: TransactionTagCrossRef)

    @Transaction
    @Query("SELECT * FROM Transactions")
    abstract suspend fun getTransactions(): List<FullTransactionLocal>

    @Transaction
    @Query("SELECT tag_id FROM TransactionTag WHERE transaction_id=:transactionId")
    abstract suspend fun getTags(transactionId: Long): List<Long>
}