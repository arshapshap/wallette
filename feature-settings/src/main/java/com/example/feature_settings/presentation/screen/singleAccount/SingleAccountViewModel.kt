package com.example.feature_settings.presentation.screen.singleAccount

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.common.domain.models.Account
import com.example.common.domain.models.AccountIcon
import com.example.common.domain.models.Currency
import com.example.common.presentation.base.BaseViewModel
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

    private val _stateLiveData = MutableLiveData<Data?>()
    val stateLiveData: LiveData<Data?>
        get() = _stateLiveData

    private val isDataValid: Boolean
        get() {
            val editingAccount = _stateLiveData.value?.account ?: return false
            return editingAccount.name != ""
                    && editingAccount.startBalance != null
                    && editingAccount.icon != AccountIcon.Empty
        }

    init {
        val editingAccount = if (account == null) EditingAccount()
            else EditingAccount(
                id = account.id,
                name = account.name,
                icon = account.icon,
                startBalance = account.startBalance,
                currency = account.currency
            )
        _stateLiveData.postValue(
            Data(
                account = editingAccount,
                icons = AccountIcon.values().filter { it.name != "Empty" },
                availableCurrencies = Currency.values().toList()
            )
        )
    }

    fun save() {
        if (!isDataValid)
            return
        val editingAccount = _stateLiveData.value?.account!!

        viewModelScope.launch {
            if (account == null)
                interactor.createAccount(
                    Account(
                        id = "",
                        name = editingAccount.name,
                        icon = editingAccount.icon,
                        currentBalance = 0.0,
                        startBalance = editingAccount.startBalance!!,
                        currency = editingAccount.currency
                    )
                )
            else
                interactor.editAccount(
                    Account(
                        id = account.id,
                        name = editingAccount.name,
                        icon = editingAccount.icon,
                        currentBalance = account.currentBalance,
                        startBalance = editingAccount.startBalance!!,
                        currency = editingAccount.currency
                    )
                )
        }
        router.openAccounts()
    }

    fun selectCurrency(currency: Currency) {
        val account = _stateLiveData.value?.account?.copy(
            currency = currency
        ) ?: return
        _stateLiveData.postValue(
            _stateLiveData.value?.copy(
                account = account
            )
        )
    }

    fun selectIcon(icon: AccountIcon) {
        val account = _stateLiveData.value?.account?.copy(
           icon = icon
        ) ?: return
        _stateLiveData.postValue(
            _stateLiveData.value?.copy(
                account = account
            )
        )
    }

    @AssistedFactory
    interface Factory {

        fun create(@Assisted account: Account?): SingleAccountViewModel
    }

    data class Data(
        val account: EditingAccount,
        val icons: List<AccountIcon>,
        val availableCurrencies: List<Currency>
    )
}