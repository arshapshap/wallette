package com.example.feature_statistics_impl.presentation.screen.transactionsList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.common.domain.models.Transaction
import com.example.common.presentation.base.BaseViewModel
import com.example.feature_statistics_impl.domain.TransactionsInteractor
import com.example.feature_statistics_impl.domain.models.TransactionGroup
import com.example.feature_statistics_impl.domain.utils.sum
import com.example.feature_statistics_impl.presentation.StatisticsRouter
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import java.util.*

class TransactionsViewModel @AssistedInject constructor(
    @Assisted("periodStart") private val periodStart: Date?,
    @Assisted("periodEnd") private val periodEnd: Date?,
    private val interactor: TransactionsInteractor,
    private val router: StatisticsRouter
) : BaseViewModel() {

    private val _stateLiveData = MutableLiveData<Data>()
    val stateLiveData : LiveData<Data>
        get() = _stateLiveData

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

    fun loadData(sortingType: SortingType = _stateLiveData.value?.sortingType ?: SortingType.ByDate) {
        viewModelScope.launch {
            val transactionGroups = interactor.getTransactionGroups(
                sortingType = sortingType,
                periodStart = periodStart,
                periodEnd = periodEnd
            )
            val state = Data(
                balance = transactionGroups
                    .flatMap { it.list }
                    .distinctBy { it.id }
                    .sum(),
                sortingType = sortingType,
                groups = transactionGroups
            )
            _stateLiveData.postValue(state)
        }
    }

    @AssistedFactory
    interface Factory {

        fun create(
            @Assisted("periodStart") periodStart: Date?,
            @Assisted("periodEnd") periodEnd: Date?
        ): TransactionsViewModel
    }

    data class Data(
        val balance: Double = 0.00,
        val sortingType: SortingType = SortingType.ByDate,
        val groups: List<TransactionGroup> = listOf()
    )
}