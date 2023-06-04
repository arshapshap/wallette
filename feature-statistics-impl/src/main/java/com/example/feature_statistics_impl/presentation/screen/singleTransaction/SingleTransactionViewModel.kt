package com.example.feature_statistics_impl.presentation.screen.singleTransaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.common.domain.models.*
import com.example.common.presentation.base.BaseViewModel
import com.example.common.presentation.liveEvent.LiveEvent
import com.example.common.presentation.liveEvent.MutableLiveEvent
import com.example.feature_statistics_impl.domain.StatisticsInteractor
import com.example.feature_statistics_impl.presentation.StatisticsRouter
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import java.util.Date

class SingleTransactionViewModel @AssistedInject constructor(
    @Assisted private val transaction: Transaction?,
    private val interactor: StatisticsInteractor,
    private val router: StatisticsRouter
) : BaseViewModel() {

    private val _startLiveData = MutableLiveData<StartData>()
    val startLiveData: LiveData<StartData>
        get() = _startLiveData

    private val _editingTransactionLiveData = MutableLiveData<EditingTransaction>()
    val editingTransactionLiveData: LiveData<EditingTransaction>
        get() = _editingTransactionLiveData

    private val _onTagRemovedLiveEvent = MutableLiveEvent<Int>()
    val onTagRemovedLiveEvent: LiveEvent<Int>
        get() = _onTagRemovedLiveEvent

    private val _onTagAddedLiveEvent = MutableLiveEvent<Tag>()
    val onTagAddedLiveEvent: LiveEvent<Tag>
        get() = _onTagAddedLiveEvent

    val tagsSuggestions
        get() = _startLiveData.value?.tags
            ?.filter { tag ->
                tag !in (_editingTransactionLiveData.value?.tags ?: listOf())
            } ?: listOf()

    val availableCategories
        get() = _startLiveData.value?.categories
            ?.filter { category ->
                category.type == _editingTransactionLiveData.value?.type
            } ?: listOf()

    private val isDataValid: Boolean
        get() {
            val editingTransaction = _editingTransactionLiveData.value ?: return false
            return editingTransaction.amount != null
                    && editingTransaction.account != null
                    && editingTransaction.date != null
                    && (editingTransaction.type != TransactionType.Transfer
                        || editingTransaction.accountDestination != null)
        }

    init {
        val editingTransaction = if (transaction == null) EditingTransaction()
        else EditingTransaction(
            id = transaction.id,
            type = transaction.type,
            date = transaction.date,
            amount = transaction.amount,
            description = transaction.description,
            account = transaction.account,
            accountDestination = transaction.accountDestination,
            category = transaction.category,
            transactionGroup = transaction.transactionGroup,
            isTransactionGroup = transaction.isTransactionGroup,
            tags = transaction.tags
        )
        _startLiveData.value = StartData(
            transaction = transaction,
            categories = interactor.getCategories(),
            accounts = interactor.getAccounts(),
            tags = interactor.getTags()
        )
        _editingTransactionLiveData.value = editingTransaction
    }

    fun save() {
        if (!isDataValid)
            return

        val editingTransaction = _editingTransactionLiveData.value!!

        val newTransaction = with (editingTransaction) {
            Transaction(
                id = transaction?.id ?: "",
                type = type,
                date = date!!,
                amount = amount!!,
                description = description,
                account = account!!,
                accountDestination = if (type == TransactionType.Transfer) accountDestination else null,
                category = if (type != TransactionType.Transfer) category else null,
                transactionGroup = transactionGroup,
                isTransactionGroup = false,
                tags = tags
            )
        }

        viewModelScope.launch {
            if (transaction == null)
                interactor.createTransaction(newTransaction)
            else
                interactor.editTransaction(newTransaction)
        }
        router.openStatistics()
    }

    fun selectCategory(category: Category) {
        val transaction = _editingTransactionLiveData.value?.copy(category = category) ?: return
        updateTransaction(transaction)
    }

    fun selectDate(date: Date) {
        val transaction = _editingTransactionLiveData.value?.copy(date = date) ?: return
        updateTransaction(transaction)
    }

    fun selectAccount(account: Account) {
        val transaction = _editingTransactionLiveData.value?.copy(account = account) ?: return
        updateTransaction(transaction)
    }

    fun selectAccountDestination(account: Account) {
        val transaction = _editingTransactionLiveData.value?.copy(accountDestination = account) ?: return
        updateTransaction(transaction)
    }

    fun selectType(type: TransactionType) {
        if (type == _editingTransactionLiveData.value?.type)
            return
        val transaction = _editingTransactionLiveData.value?.copy(
            type = type,
            category = null
        ) ?: return
        updateTransaction(transaction)
    }

    fun removeTag(tag: Tag) {
        val tags = _editingTransactionLiveData.value?.tags?.toMutableList() ?: return
        val index = tags.indexOf(tag)
        tags.remove(tag)
        val transaction = _editingTransactionLiveData.value?.copy(tags = tags) ?: return

        _onTagRemovedLiveEvent.post(index)
        updateTransaction(transaction)
    }

    fun addTag(index: Int) {
        val tags = _editingTransactionLiveData.value?.tags?.toMutableList() ?: return
        val tag = tagsSuggestions[index]

        tags.add(tag)
        val transaction = _editingTransactionLiveData.value?.copy(tags = tags) ?: return

        _onTagAddedLiveEvent.post(tag)
        updateTransaction(transaction)
    }

    private fun updateTransaction(transaction: EditingTransaction) {
        _editingTransactionLiveData.value = transaction
    }

    @AssistedFactory
    interface Factory {

        fun create(@Assisted transaction: Transaction?): SingleTransactionViewModel
    }

    data class StartData(
        val transaction: Transaction?,
        val categories: List<Category>,
        val accounts: List<Account>,
        val tags: List<Tag>
    )
}