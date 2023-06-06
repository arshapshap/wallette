package com.example.feature_statistics_impl.presentation.screen.transactionsList.groupsRecyclerView.transactionGroups

import com.example.common.domain.models.Transaction

interface TransactionGroup {
    val list: List<Transaction>
    var isExpanded: Boolean
}