package com.example.common.domain.models

import androidx.annotation.DrawableRes

interface Icon {

    @get:DrawableRes
    val drawableRes: Int
}