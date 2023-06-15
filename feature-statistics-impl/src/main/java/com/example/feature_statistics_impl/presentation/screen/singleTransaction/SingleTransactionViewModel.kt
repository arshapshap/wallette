package com.example.feature_statistics_impl.presentation.screen.singleTransaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.common.domain.models.*
import com.example.common.domain.models.enums.TransactionType
import com.example.common.presentation.base.BaseViewModel
import com.example.common.presentation.extensions.round
import com.example.common.presentation.liveEvent.LiveEvent
import com.example.common.presentation.liveEvent.MutableLiveEvent
import com.example.feature_statistics_impl.domain.StatisticsInteractor
import com.example.feature_statistics_impl.presentation.StatisticsRouter
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.math.absoluteValue

class   SingleTransactionViewModel @AssistedInject constructor(
    @Assisted private val transaction: Transaction?,
    @Assisted private val transactionType: TransactionType?,
    private val interactor: StatisticsInteractor,
    private val router: StatisticsRouter
) : BaseViewModel() {

    private val _transactionLiveData = MutableLiveData<Transaction?>()
    val transactionLiveData: LiveData<Transaction?>
        get() = _transactionLiveData

    private val _editingTransactionLiveData = MutableLiveData<EditingTransaction>()
    val editingTransactionLiveData: LiveData<EditingTransaction>
        get() = _editingTransactionLiveData

    private val _onTagRemovedLiveEvent = MutableLiveEvent<Int>()
    val onTagRemovedLiveEvent: LiveEvent<Int>
        get() = _onTagRemovedLiveEvent

    private val _onTagAddedLiveEvent = MutableLiveEvent<Tag>()
    val onTagAddedLiveEvent: LiveEvent<Tag>
        get() = _onTagAddedLiveEvent

    private val _onAccountsRequestedLiveEvent = MutableLiveEvent<List<Account>>()
    val onAccountsRequestedLiveEvent: LiveEvent<List<Account>>
        get() = _onAccountsRequestedLiveEvent

    private val _onAccountsToTransferRequestedLiveEvent = MutableLiveEvent<List<Account>>()
    val onAccountsToTransferRequestedLiveEvent: LiveEvent<List<Account>>
        get() = _onAccountsToTransferRequestedLiveEvent

    private val _onCategoriesRequestedLiveEvent = MutableLiveEvent<List<Category>>()
    val onCategoriesRequestedLiveEvent: LiveEvent<List<Category>>
        get() = _onCategoriesRequestedLiveEvent

    private val _onTagsRequestedLiveEvent = MutableLiveEvent<List<Tag>>()
    val onTagsRequestedLiveEvent: LiveEvent<List<Tag>>
        get() = _onTagsRequestedLiveEvent

    private val isDataValid: Boolean
        get() {
            val editingTransaction = _editingTransactionLiveData.value ?: return false
            return editingTransaction.amount != null
                    && editingTransaction.account != null
                    && (editingTransaction.type != TransactionType.Transfer
                        || editingTransaction.accountDestination != null)
        }

    init {
        viewModelScope.launch {
            val editingTransaction = if (transaction == null) EditingTransaction(
                type = transactionType ?: TransactionType.Expense,
                account = interactor.getAccounts().firstOrNull()
            )
            else EditingTransaction(
                id = transaction.id,
                type = transaction.type,
                date = transaction.date,
                amount = transaction.amount.round(2),
                description = transaction.description,
                account = transaction.account,
                accountDestination = transaction.accountDestination,
                category = transaction.category,
                tags = transaction.tags
            )
            _transactionLiveData.postValue(transaction)
            _editingTransactionLiveData.postValue(editingTransaction)
        }
        updateTagsSuggestions()
    }

    fun save() {
        if (!isDataValid)
            return

        val editingTransaction = _editingTransactionLiveData.value!!
        var amount = editingTransaction.amount!!.absoluteValue
        if (editingTransaction.type == TransactionType.Expense)
            amount *= -1

        val newTransaction = with (editingTransaction) {
            Transaction(
                id = transaction?.id ?: 0,
                type = type,
                date = date,
                amount = amount.round(2),
                description = description,
                account = account!!,
                accountDestination = if (type == TransactionType.Transfer) accountDestination else null,
                category = if (type != TransactionType.Transfer) category else null,
                tags = tags
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            if (transaction == null)
                interactor.createTransaction(newTransaction)
            else
                interactor.editTransaction(newTransaction)
        }
        router.close()
    }

    fun delete() {
        if (transaction == null) return
        viewModelScope.launch {
            interactor.deleteTransaction(transaction)
        }
        router.close()
    }

    fun editAmount(amountString: String) {
        val amount = amountString.toDoubleOrNull()?.round(2)
        val transaction = _editingTransactionLiveData.value?.copy(amount = amount) ?: return
        updateTransaction(transaction)
    }

    fun editDescription(description: String) {
        val transaction = _editingTransactionLiveData.value?.copy(description = description) ?: return
        updateTransaction(transaction)
    }

    fun getAccounts() {
        viewModelScope.launch {
            _onAccountsRequestedLiveEvent.post(interactor.getAccounts())
        }
    }

    fun getAccountsToTransfer() {
        viewModelScope.launch {
            val currentAccount = editingTransactionLiveData.value?.account
            _onAccountsToTransferRequestedLiveEvent.post(interactor.getAccounts().filter { it != currentAccount })
        }
    }

    fun getCategories() {
        viewModelScope.launch {
            _onCategoriesRequestedLiveEvent.post(interactor
                .getCategories()
                .filter { category ->
                    category.type == editingTransactionLiveData.value?.type
                }
            )
        }
    }

    private fun updateTagsSuggestions() {
        viewModelScope.launch {
            _onTagsRequestedLiveEvent.post(interactor
                .getTags()
                .filter { tag ->
                    tag !in (_editingTransactionLiveData.value?.tags ?: listOf())
                }
            )
        }
    }

    fun selectAccount(account: Account) {
        val currentTransaction = _editingTransactionLiveData.value ?: return
        currentTransaction.let {
            val transaction = it.copy(
                account = account,
                accountDestination = if (account == it.accountDestination) it.account else it.accountDestination
            )
            updateTransaction(transaction)

        }
    }

    fun selectAccountDestination(account: Account) {
        val transaction = _editingTransactionLiveData.value?.copy(accountDestination = account) ?: return
        updateTransaction(transaction)
    }

    fun selectCategory(category: Category) {
        val transaction = _editingTransactionLiveData.value?.copy(category = category) ?: return
        updateTransaction(transaction)
    }

    fun selectDate(date: Date) {
        val transaction = _editingTransactionLiveData.value?.copy(date = date) ?: return
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
        updateTagsSuggestions()
        updateTransaction(transaction)
    }

    fun addTag(index: Int) {
        val tags = _editingTransactionLiveData.value?.tags?.toMutableList() ?: return
        val tag = _onTagsRequestedLiveEvent.value?.message?.get(index) ?: return

        tags.add(tag)
        val transaction = _editingTransactionLiveData.value?.copy(tags = tags) ?: return

        _onTagAddedLiveEvent.post(tag)
        updateTagsSuggestions()
        updateTransaction(transaction)
    }

    private fun updateTransaction(transaction: EditingTransaction) {
        _editingTransactionLiveData.value = transaction
    }

    @AssistedFactory
    interface Factory {

        fun create(
            @Assisted transaction: Transaction?,
            @Assisted transactionType: TransactionType?
        ): SingleTransactionViewModel
    }
}