package com.example.feature_statistics_impl.presentation.screen.transactionsList

import com.example.feature_statistics_impl.presentation.screen.transactionsList.groupsRecyclerView.transactionGroups.TransactionGroup

data class DataState(
    val balance: Int = 0,
    val sortingType: SortingType = SortingType.ByDate,
    val groups: List<TransactionGroup> = listOf()
)
