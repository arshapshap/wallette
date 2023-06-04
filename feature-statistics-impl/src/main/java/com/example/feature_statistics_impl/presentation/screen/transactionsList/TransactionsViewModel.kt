package com.example.feature_statistics_impl.presentation.screen.transactionsList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.common.domain.models.Transaction
import com.example.common.presentation.base.BaseViewModel
import com.example.feature_statistics_impl.presentation.StatisticsRouter
import com.example.feature_statistics_impl.domain.StatisticsInteractor
import com.example.feature_statistics_impl.presentation.screen.transactionsList.groupsRecyclerView.transactionGroups.TransactionGroup
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class TransactionsViewModel @AssistedInject constructor(
    private val interactor: StatisticsInteractor,
    private val router: StatisticsRouter
) : BaseViewModel() {

    private val _stateLiveData = MutableLiveData<Data>()
    val stateLiveData : LiveData<Data>
        get() = _stateLiveData

    init {
        loadData(SortingType.ByDate)
    }

    fun changeSortingType() {
        val nextType = when (_stateLiveData.value?.sortingType) {
            SortingType.ByCategory -> SortingType.ByTag
            SortingType.ByTag -> SortingType.ByDate
            else -> SortingType.ByCategory
        }
        loadData(nextType)
    }

    fun openTransaction(transaction: Transaction) {
        router.openSingleTransaction(transaction)
    }

    private fun loadData(sortingType: SortingType) {
        viewModelScope.launch {
            val transactionGroups = interactor.getTransactionGroups(sortingType = sortingType)
            val state = Data(
                balance = transactionGroups
                    .flatMap { it.list }
                    .distinctBy { it.id }
                    .sumOf { it.amount },
                sortingType = sortingType,
                groups = transactionGroups
            )
            _stateLiveData.postValue(state)
        }
    }

    @AssistedFactory
    interface Factory {

        fun create(): TransactionsViewModel
    }

    data class Data(
        val balance: Double = 0.00,
        val sortingType: SortingType = SortingType.ByDate,
        val groups: List<TransactionGroup> = listOf()
    )
}