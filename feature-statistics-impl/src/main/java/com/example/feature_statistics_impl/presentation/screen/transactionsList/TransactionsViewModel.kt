package com.example.feature_statistics_impl.presentation.screen.transactionsList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.common.presentation.base.BaseViewModel
import com.example.feature_statistics_impl.presentation.StatisticsRouter
import com.example.feature_statistics_impl.domain.StatisticsInteractor
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class TransactionsViewModel @AssistedInject constructor(
    private val interactor: StatisticsInteractor,
    private val router: StatisticsRouter
) : BaseViewModel() {

    private val _stateLiveData = MutableLiveData<DataState>()
    val stateLiveData : LiveData<DataState>
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

    fun openTransaction(id: String) {
        router.openTransaction(id)
    }

    private fun loadData(sortingType: SortingType) {
        viewModelScope.launch {
            val transactionGroups = interactor.getTransactionGroups(sortingType = sortingType)
            val state = DataState(
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
}