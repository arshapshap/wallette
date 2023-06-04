package com.example.core_db.dao

import androidx.room.*
import com.example.core_db.models.entities.AccountLocal

@Dao
abstract class AccountDao {

    @Insert
    abstract suspend fun addAccount(accountLocal: AccountLocal)

    @Update
    abstract suspend fun updateAccount(accountLocal: AccountLocal)

    @Delete
    abstract suspend fun deleteAccount(accountLocal: AccountLocal)

    @Query("SELECT * FROM Accounts")
    abstract suspend fun getAccounts(): List<AccountLocal>
}