package com.example.core_db.models

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.core_db.models.entities.TagLocal
import com.example.core_db.models.entities.TransactionLocal

data class TransactionWithTagsLocal(
    @Embedded val transaction: TransactionLocal,
    @Relation(
         parentColumn = "transaction_id",
         entityColumn = "tag_id",
         associateBy = Junction(TransactionTagCrossRef::class)
    )
    val tags: List<TagLocal>
)