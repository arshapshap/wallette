package com.example.feature_settings.presentation.screen.singleTag

import androidx.annotation.ColorInt

data class EditingTag(
    val id: Long = 0,
    val name: String = "",
    @ColorInt val color: Int? = null
)