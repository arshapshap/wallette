package com.example.data.managers

import android.content.SharedPreferences
import com.example.common.data.SettingsManager
import com.example.common.domain.models.Account
import com.example.common.domain.models.enums.Currency
import com.example.common.domain.models.enums.DayOfWeek
import com.example.common.domain.models.enums.Language
import com.example.common.domain.models.enums.TimePeriod
import javax.inject.Inject

class SettingsManagerImpl @Inject constructor(
    private val sharedPrefs: SharedPreferences
): SettingsManager {

    companion object {
        private const val MAIN_CURRENCY_KEY = "MAIN_CURRENCY_KEY"
        private const val LANGUAGE_KEY = "LANGUAGE_KEY"
        private const val FIRST_DAY_OF_WEEK_KEY = "FIRST_DAY_OF_WEEK_KEY"
        private const val FIRST_DAY_OF_MONTH_KEY = "FIRST_DAY_OF_MONTH_KEY"
        private const val TIME_PERIOD_KEY = "TIME_PERIOD_KEY"
        private const val ACCOUNT_ID_KEY = "ACCOUNT_KEY"
    }

    override fun setMainCurrency(currency: Currency) {
        sharedPrefs.edit().putString(MAIN_CURRENCY_KEY, currency.name).apply()
    }

    override fun setLanguage(language: Language) {
        sharedPrefs.edit().putString(LANGUAGE_KEY, language.name).apply()
    }

    override fun setFirstDayOfWeek(dayOfWeek: DayOfWeek) {
        sharedPrefs.edit().putString(FIRST_DAY_OF_WEEK_KEY, dayOfWeek.name).apply()
    }

    override fun setFirstDayOfMonth(day: Int) {
        sharedPrefs.edit().putInt(FIRST_DAY_OF_MONTH_KEY, day).apply()
    }

    override fun setViewedTimePeriod(timePeriod: TimePeriod) {
        sharedPrefs.edit().putString(TIME_PERIOD_KEY, timePeriod.name).apply()
    }

    override fun setViewedAccount(account: Account) {
        sharedPrefs.edit().putLong(ACCOUNT_ID_KEY, account.id).apply()
    }

    override fun getMainCurrency(): Currency? {
        val currencyName = sharedPrefs.getString(MAIN_CURRENCY_KEY, null) ?: return null
        return Currency.valueOf(currencyName)
    }

    override fun getLanguage(): Language? {
        val languageName = sharedPrefs.getString(LANGUAGE_KEY, null) ?: return null
        return Language.valueOf(languageName)
    }

    override fun getFirstDayOfWeek(): DayOfWeek? {
        val dayOfWeekName = sharedPrefs.getString(FIRST_DAY_OF_WEEK_KEY, null) ?: return null
        return DayOfWeek.valueOf(dayOfWeekName)
    }

    override fun getFirstDayOfMonth(): Int {
        return sharedPrefs.getInt(FIRST_DAY_OF_MONTH_KEY, 0)
    }

    override fun getViewedTimePeriod(): TimePeriod? {
        val timePeriod = sharedPrefs.getString(TIME_PERIOD_KEY, null) ?: return null
        return TimePeriod.valueOf(timePeriod)
    }

    override fun getViewedAccountId(): Long {
        return sharedPrefs.getLong(FIRST_DAY_OF_MONTH_KEY, 0)
    }

}