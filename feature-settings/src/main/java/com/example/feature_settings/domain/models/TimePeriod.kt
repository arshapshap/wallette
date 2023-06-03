package com.example.feature_settings.domain.models

import androidx.annotation.StringRes
import com.example.feature_settings.R

enum class TimePeriod(@StringRes val stringRes: Int) {
    Day(R.string.day),
    Week(R.string.week),
    Month(R.string.month),
    Year(R.string.year),
    All(R.string.all)
}