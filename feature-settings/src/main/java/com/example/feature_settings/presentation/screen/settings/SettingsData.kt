package com.example.feature_settings.presentation.screen.settings

import com.example.common.domain.models.Currency
import com.example.feature_settings.domain.models.DayOfWeek
import com.example.feature_settings.domain.models.Language
import com.example.feature_settings.domain.models.TimePeriod

data class SettingsData(
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