package com.example.feature_settings.domain.models

import androidx.annotation.StringRes
import com.example.feature_settings.R

enum class DayOfWeek(@StringRes val stringRes: Int) {
    Monday(R.string.monday),
    Tuesday(R.string.tuesday),
    Wednesday(R.string.wednesday),
    Thursday(R.string.thursday),
    Friday(R.string.friday),
    Saturday(R.string.saturday),
    Sunday(R.string.sunday)
}