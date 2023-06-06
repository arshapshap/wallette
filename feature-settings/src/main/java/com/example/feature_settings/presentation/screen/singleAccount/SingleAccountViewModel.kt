package com.example.feature_settings.presentation.screen.singleAccount

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.common.domain.models.Account
import com.example.common.domain.models.enums.AccountIcon
import com.example.common.domain.models.enums.Currency
import com.example.common.presentation.base.BaseViewModel
import com.example.common.presentation.extensions.round
import com.example.feature_settings.domain.SettingsInteractor
import com.example.feature_settings.presentation.SettingsRouter
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class SingleAccountViewModel @AssistedInject constructor(
    @Assisted private val account: Account?,
    private val interactor: SettingsInteractor,
    private val router: SettingsRouter
) : BaseViewModel() {

    private val _startLiveData = MutableLiveData<StartData>()
    val startLiveData: LiveData<StartData>
        get() = _startLiveData

    private val _editingAccountLiveData = MutableLiveData<EditingAccount>()
    val editingAccountLiveData: LiveData<EditingAccount>
        get() = _editingAccountLiveData

    private val isDataValid: Boolean
        get() {
            val editingAccount = _editingAccountLiveData.value ?: return false
            return editingAccount.name != ""
                    && editingAccount.icon != AccountIcon.Empty
        }

    init {
        val editingAccount = if (account == null) EditingAccount()
            else EditingAccount(
                id = account.id,
                name = account.name,
                icon = account.icon,
                startBalance = account.startBalance,
                currentBalance = account.currentBalance,
                currency = account.currency
            )
        viewModelScope.launch {
            _startLiveData.postValue(
                StartData(
                    account = account,
                    icons = AccountIcon.values().filter { it.name != "Empty" },
                    availableCurrencies = Currency.values().toList(),
                    canBeDeleted = account != null && interactor.getAccounts().size > 1
                )
            )
            _editingAccountLiveData.postValue(editingAccount)
        }
    }

    fun save() {
        if (!isDataValid)
            return

        val editingAccount = _editingAccountLiveData.value!!
        val newAccount = Account(
            id = account?.id ?: 0,
            name = editingAccount.name,
            icon = editingAccount.icon,
            currentBalance = editingAccount.currentBalance.round(2),
            startBalance = editingAccount.startBalance.round(2),
            currency = editingAccount.currency
        )
        viewModelScope.launch {
            if (account == null)
                interactor.createAccount(newAccount)
            else
                interactor.editAccount(newAccount)
        }
        router.close()
    }

    fun delete() {
        if  (account == null) return
        viewModelScope.launch {
            interactor.deleteAccount(account)
            router.close()
        }
    }

    fun selectCurrency(currency: Currency) {
        val account = _editingAccountLiveData.value?.copy(currency = currency) ?: return
        updateAccount(account)
    }

    fun selectIcon(icon: AccountIcon) {
        val account = _editingAccountLiveData.value?.copy(icon = icon) ?: return
        updateAccount(account)
    }

    fun editStartBalance(startBalanceString: String) {
        val startBalance = startBalanceString.toDoubleOrNull() ?: return

        val oldStartBalance = _editingAccountLiveData.value?.startBalance ?: return
        val currentBalance = _editingAccountLiveData.value?.currentBalance ?: return
        val account = _editingAccountLiveData.value?.copy(
            startBalance = startBalance.round(2),
            currentBalance = (currentBalance - oldStartBalance + startBalance).round(2)
        ) ?: return
        updateAccount(account)
    }

    fun editName(name: String) {
        val account = _editingAccountLiveData.value?.copy(name = name) ?: return
        updateAccount(account)
    }

    private fun updateAccount(account: EditingAccount) {
        _editingAccountLiveData.value = account
    }

    @AssistedFactory
    interface Factory {

        fun create(@Assisted account: Account?): SingleAccountViewModel
    }

    data class StartData(
        val account: Account?,
        val icons: List<AccountIcon>,
        val availableCurrencies: List<Currency>,
        val canBeDeleted: Boolean
    )
}