package com.example.feature_settings.domain.models

import androidx.annotation.StringRes
import com.example.feature_settings.R

enum class Language(@StringRes val stringRes: Int) {
    RU(R.string.language_ru),
    EN(R.string.language_en)
}