package com.example.feature_settings.presentation.screen.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.common.domain.models.enums.Currency
import com.example.common.presentation.base.BaseViewModel
import com.example.feature_settings.domain.SettingsInteractor
import com.example.feature_settings.domain.models.DayOfWeek
import com.example.feature_settings.domain.models.Language
import com.example.feature_settings.domain.models.TimePeriod
import com.example.feature_settings.presentation.SettingsRouter
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import kotlin.Int

class SettingsViewModel @AssistedInject constructor(
    private val router: SettingsRouter,
    private val interactor: SettingsInteractor
) : BaseViewModel() {

    private val _settingsLiveData = MutableLiveData<Settings>()
    val settingsLiveData : LiveData<Settings>
        get() = _settingsLiveData

    private val _isSynchronized = MutableLiveData(false)
    val isSynchronized : LiveData<Boolean>
        get() = _isSynchronized

    init {
        val settings = Settings(
            currency = Currency.RUB,
            language = Language.RU,
            firstDayOfWeek = DayOfWeek.Monday,
            firstDayOfMonth = 1,
            timePeriod = TimePeriod.Day,
            availableCurrencies = Currency.values().toList(),
            availableLanguages = Language.values().toList(),
            daysOfWeek = DayOfWeek.values().toList(),
            daysOfMonth = (1..31).toList(),
            availableTimePeriods = TimePeriod.values().toList()
        )
        _settingsLiveData.postValue(settings)

        viewModelScope.launch {
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
        _settingsLiveData.postValue(
            _settingsLiveData.value?.copy(
                currency = currency
            )
        )
    }

    fun selectLanguage(language: Language) {
        _settingsLiveData.postValue(
            _settingsLiveData.value?.copy(
                language = language
            )
        )
    }

    fun selectFirstDayOfWeek(firstDayOfWeek: DayOfWeek) {
        _settingsLiveData.postValue(
            _settingsLiveData.value?.copy(
                firstDayOfWeek = firstDayOfWeek
            )
        )
    }

    fun selectFirstDayOfMonth(firstDayOfMonth: Int) {
        _settingsLiveData.postValue(
            _settingsLiveData.value?.copy(
                firstDayOfMonth = firstDayOfMonth
            )
        )
    }

    fun selectTimePeriod(timePeriod: TimePeriod) {
        _settingsLiveData.postValue(
            _settingsLiveData.value?.copy(
                timePeriod = timePeriod
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
        val availableCurrencies: List<Currency>,
        val availableLanguages: List<Language>,
        val daysOfWeek: List<DayOfWeek>,
        val daysOfMonth: List<Int>,
        val availableTimePeriods: List<TimePeriod>
    )
}