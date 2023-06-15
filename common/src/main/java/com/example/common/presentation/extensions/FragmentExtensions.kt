package com.example.common.presentation.extensions

import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.fragment.app.Fragment

@ColorInt
fun Fragment.getColor(@AttrRes attr: Int): Int {
    val typedValue = TypedValue()
    activity?.theme?.resolveAttribute(attr, typedValue, true)
    return typedValue.data
}

@ColorInt
fun Fragment.getColorPrimary(): Int = getColor(androidx.appcompat.R.attr.colorPrimary)

@ColorInt
fun Fragment.getColorOnPrimary(): Int = getColor(com.google.android.material.R.attr.colorOnPrimary)

@ColorInt
fun Fragment.getColorOnBackground(): Int = getColor(com.google.android.material.R.attr.colorOnBackground)