package com.example.common.domain.models.enums

import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes

interface Icon {

    @get:DrawableRes
    val drawableRes: Int
    @get:ColorInt
    val colorInt: Int
}