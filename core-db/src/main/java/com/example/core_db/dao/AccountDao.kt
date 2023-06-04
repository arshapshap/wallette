package com.example.core_db.dao

import androidx.room.*
import com.example.core_db.models.entities.AccountLocal

@Dao
abstract class AccountDao {

    @Insert
    abstract fun addAccount(accountLocal: AccountLocal)

    @Update
    abstract fun updateAccount(accountLocal: AccountLocal)

    @Delete
    abstract fun deleteAccount(accountLocal: AccountLocal)

    @Query("SELECT * FROM Accounts")
    abstract fun getAccounts(): List<AccountLocal>
}