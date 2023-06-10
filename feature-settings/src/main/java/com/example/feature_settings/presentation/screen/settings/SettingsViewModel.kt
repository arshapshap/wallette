package com.example.feature_settings.presentation.screen.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.common.domain.models.Account
import com.example.common.domain.models.enums.Currency
import com.example.common.presentation.base.BaseViewModel
import com.example.feature_settings.domain.SettingsInteractor
import com.example.common.domain.models.enums.DayOfWeek
import com.example.common.domain.models.enums.Language
import com.example.common.domain.models.enums.TimePeriod
import com.example.feature_settings.domain.SettingsAccountsInteractor
import com.example.feature_settings.presentation.SettingsRouter
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import kotlin.Int

class SettingsViewModel @AssistedInject constructor(
    private val router: SettingsRouter,
    private val interactor: SettingsInteractor,
    private val accountsInteractor: SettingsAccountsInteractor
) : BaseViewModel() {

    private val _settingsLiveData = MutableLiveData<Settings>()
    val settingsLiveData : LiveData<Settings>
        get() = _settingsLiveData

    private val _isSynchronized = MutableLiveData(false)
    val isSynchronized : LiveData<Boolean>
        get() = _isSynchronized

    init {
        viewModelScope.launch {
            val settings = Settings(
                currency = interactor.getMainCurrency(),
                language = interactor.getLanguage(),
                firstDayOfWeek = interactor.getFirstDayOfWeek(),
                firstDayOfMonth = interactor.getFirstDayOfMonth(),
                timePeriod = interactor.getViewedTimePeriod(),
                viewedAccount = interactor.getViewedAccount(),
                accounts = accountsInteractor.getAccounts(),
                availableCurrencies = Currency.values().toList(),
                availableLanguages = Language.values().toList(),
                daysOfWeek = DayOfWeek.values().toList(),
                daysOfMonth = (1..31).toList(),
                availableTimePeriods = TimePeriod.values().toList()
            )
            _settingsLiveData.postValue(settings)
            _isSynchronized.postValue(interactor.checkIsAuthorized())
        }
    }

    fun enableSynchronization() {
        if (_isSynchronized.value == true)
            return
        router.openLoginPage()
    }

    fun disableSynchronization() {
        if (_isSynchronized.value == false)
            return
        viewModelScope.launch {
            interactor.logout()
            _isSynchronized.postValue(false)
        }
    }

    fun openCategories() {
        router.openCategories()
    }

    fun openTags() {
        router.openTags()
    }

    fun openAccounts() {
        router.openAccounts()
    }

    fun selectCurrency(currency: Currency) {
        viewModelScope.launch {
            interactor.setMainCurrency(currency)
        }
        _settingsLiveData.postValue(
            _settingsLiveData.value?.copy(
                currency = currency
            )
        )
    }

    fun selectLanguage(language: Language) {
        viewModelScope.launch {
            interactor.setLanguage(language)
        }
        _settingsLiveData.postValue(
            _settingsLiveData.value?.copy(
                language = language
            )
        )
    }

    fun selectFirstDayOfWeek(firstDayOfWeek: DayOfWeek) {
        viewModelScope.launch {
            interactor.setFirstDayOfWeek(firstDayOfWeek)
        }
        _settingsLiveData.postValue(
            _settingsLiveData.value?.copy(
                firstDayOfWeek = firstDayOfWeek
            )
        )
    }

    fun selectFirstDayOfMonth(firstDayOfMonth: Int) {
        viewModelScope.launch {
            interactor.setFirstDayOfMonth(firstDayOfMonth)
        }
        _settingsLiveData.postValue(
            _settingsLiveData.value?.copy(
                firstDayOfMonth = firstDayOfMonth
            )
        )
    }

    fun selectTimePeriod(timePeriod: TimePeriod) {
        viewModelScope.launch {
            interactor.setViewedTimePeriod(timePeriod)
        }
        _settingsLiveData.postValue(
            _settingsLiveData.value?.copy(
                timePeriod = timePeriod
            )
        )
    }

    fun selectViewedAccount(account: Account?) {
        viewModelScope.launch {
            interactor.setViewedAccount(account)
        }
        _settingsLiveData.postValue(
            _settingsLiveData.value?.copy(
                viewedAccount = account
            )
        )
    }

    @AssistedFactory
    interface Factory {

        fun create(): SettingsViewModel
    }

    data class Settings(
        val currency: Currency,
        val language: Language,
        val firstDayOfWeek: DayOfWeek,
        val firstDayOfMonth: Int,
        val timePeriod: TimePeriod,
        val viewedAccount: Account?,
        val accounts: List<Account>,
        val availableCurrencies: List<Currency>,
        val availableLanguages: List<Language>,
        val daysOfWeek: List<DayOfWeek>,
        val daysOfMonth: List<Int>,
        val availableTimePeriods: List<TimePeriod>
    )
}