package com.example.feature_settings.domain

import com.example.common.data.SettingsManager
import com.example.common.domain.models.Account
import com.example.common.domain.models.enums.Currency
import com.example.common.domain.models.enums.DayOfWeek
import com.example.common.domain.models.enums.Language
import com.example.common.domain.models.enums.TimePeriod
import com.example.common.domain.repositories.AccountRepository
import com.example.common.domain.repositories.AuthorizationRepository
import javax.inject.Inject

class SettingsInteractor @Inject constructor(
    private val authorizationRepository: AuthorizationRepository,
    private val accountRepository: AccountRepository,
    private val settingsManager: SettingsManager
) {
    suspend fun checkIsAuthorized(): Boolean {
        return authorizationRepository.checkIsAuthorized()
    }

    suspend fun logout() {
        return authorizationRepository.logout()
    }

    fun setMainCurrency(currency: Currency) {
        settingsManager.setMainCurrency(currency)
    }

    fun setLanguage(language: Language) {
        settingsManager.setLanguage(language)
    }

    fun setFirstDayOfWeek(dayOfWeek: DayOfWeek) {
        settingsManager.setFirstDayOfWeek(dayOfWeek)
    }

    fun setFirstDayOfMonth(day: Int) {
        if (day < 1 || day > 31) throw IllegalArgumentException("Day of month must be between 1 and 31")
        settingsManager.setFirstDayOfMonth(day)
    }

    fun setViewedTimePeriod(timePeriod: TimePeriod) {
        settingsManager.setViewedTimePeriod(timePeriod)
    }

    fun setViewedAccount(account: Account?) {
        settingsManager.setViewedAccount(account)
    }

    fun getMainCurrency(): Currency = settingsManager.getMainCurrency() ?: Currency.RUB // TODO: дефолтные значения

    fun getLanguage(): Language = settingsManager.getLanguage() ?: Language.RU

    fun getFirstDayOfWeek(): DayOfWeek = settingsManager.getFirstDayOfWeek() ?: DayOfWeek.Monday

    fun getFirstDayOfMonth(): Int = settingsManager.getFirstDayOfMonth() ?: 1

    fun getViewedTimePeriod(): TimePeriod = settingsManager.getViewedTimePeriod() ?: TimePeriod.All

    suspend fun getViewedAccount(): Account? {
        val accountId = settingsManager.getViewedAccountId()
        if (accountId == 0L)
            return null
        return accountRepository.getAccountById(accountId)
    }
}