package com.example.feature_settings.presentation.screen.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.common.domain.models.Currency
import com.example.common.presentation.base.BaseViewModel
import com.example.feature_settings.domain.models.DayOfWeek
import com.example.feature_settings.domain.models.Language
import com.example.feature_settings.domain.models.TimePeriod
import com.example.feature_settings.presentation.SettingsRouter
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlin.Int

class SettingsViewModel @AssistedInject constructor(
    private val router: SettingsRouter
) : BaseViewModel() {

    private val _dataLiveData = MutableLiveData<SettingsData>()
    val dataLiveData : LiveData<SettingsData>
        get() = _dataLiveData

    init {
        val data = SettingsData(
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
        _dataLiveData.postValue(data)
    }

    fun enableSynchronization() {

    }

    fun openCategories() {

    }

    fun openTags() {

    }

    fun openAccounts() {

    }

    fun selectCurrency(currency: Currency) {
        _dataLiveData.postValue(
            _dataLiveData.value?.copy(
                currency = currency
            )
        )
    }

    fun selectLanguage(language: Language) {
        _dataLiveData.postValue(
            _dataLiveData.value?.copy(
                language = language
            )
        )
    }

    fun selectFirstDayOfWeek(firstDayOfWeek: DayOfWeek) {
        _dataLiveData.postValue(
            _dataLiveData.value?.copy(
                firstDayOfWeek = firstDayOfWeek
            )
        )
    }

    fun selectFirstDayOfMonth(firstDayOfMonth: Int) {
        _dataLiveData.postValue(
            _dataLiveData.value?.copy(
                firstDayOfMonth = firstDayOfMonth
            )
        )
    }

    fun selectTimePeriod(timePeriod: TimePeriod) {
        _dataLiveData.postValue(
            _dataLiveData.value?.copy(
                timePeriod = timePeriod
            )
        )
    }

    @AssistedFactory
    interface Factory {

        fun create(): SettingsViewModel
    }
}