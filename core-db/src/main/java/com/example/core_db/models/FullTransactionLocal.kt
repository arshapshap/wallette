package com.example.core_db.models

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.core_db.models.entities.AccountLocal
import com.example.core_db.models.entities.CategoryLocal
import com.example.core_db.models.entities.TagLocal
import com.example.core_db.models.entities.TransactionLocal

data class FullTransactionLocal (
    @Embedded val transaction: TransactionLocal,
    @Relation(
        parentColumn = "account_id",
        entityColumn = "account_id"
    )
    val account: AccountLocal,
    @Relation(
        parentColumn = "account_destination_id",
        entityColumn = "account_id"
    )
    val accountDestination: AccountLocal?,
    @Relation(
        parentColumn = "category_id",
        entityColumn = "category_id"
    )
    val category: CategoryLocal?,
    @Relation(
         parentColumn = "transaction_id",
         entityColumn = "tag_id",
         associateBy = Junction(TransactionTagCrossRef::class)
    )
    val tags: List<TagLocal>
)