package com.example.common.data

import com.example.common.domain.models.Account
import com.example.common.domain.models.enums.Currency
import com.example.common.domain.models.enums.DayOfWeek
import com.example.common.domain.models.enums.Language
import com.example.common.domain.models.enums.TimePeriod

interface SettingsManager {

    fun setMainCurrency(currency: Currency)

    fun setLanguage(language: Language)

    fun setFirstDayOfWeek(dayOfWeek: DayOfWeek)

    fun setFirstDayOfMonth(day: Int)

    fun setViewedTimePeriod(timePeriod: TimePeriod)

    fun setViewedAccount(account: Account?)

    fun getMainCurrency(): Currency

    fun getLanguage(): Language

    fun getFirstDayOfWeek(): DayOfWeek

    fun getFirstDayOfMonth(): Int

    fun getViewedTimePeriod(): TimePeriod

    fun getViewedAccountId(): Long?
}