package com.example.core_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.core_db.dao.AccountDao
import com.example.core_db.dao.CategoryDao
import com.example.core_db.dao.TagDao
import com.example.core_db.dao.TransactionDao
import com.example.core_db.models.TransactionTagCrossRef
import com.example.core_db.models.entities.AccountLocal
import com.example.core_db.models.entities.CategoryLocal
import com.example.core_db.models.entities.TagLocal
import com.example.core_db.models.entities.TransactionLocal

@Database(
    version = 1,
    entities = [
        AccountLocal::class,
        CategoryLocal::class,
        TagLocal::class,
        TransactionLocal::class,
        TransactionTagCrossRef::class
    ])
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun get(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                    AppDatabase::class.java, "app.db")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!
        }
    }

    abstract fun accountDao(): AccountDao

    abstract fun categoryDao(): CategoryDao

    abstract fun tagDao(): TagDao

    abstract fun transactionDao(): TransactionDao
}