package com.example.common.presentation.extensions

import android.util.TypedValue
import androidx.annotation.ColorInt
import androidx.fragment.app.Fragment

@ColorInt
fun Fragment.getColorPrimary(): Int {
    val typedValue = TypedValue()
    activity?.theme?.resolveAttribute(androidx.appcompat.R.attr.colorPrimary, typedValue, true)
    return typedValue.data
}

@ColorInt
fun Fragment.getColorOnPrimary(): Int {
    val typedValue = TypedValue()
    activity?.theme?.resolveAttribute(com.google.android.material.R.attr.colorOnPrimary, typedValue, true)
    return typedValue.data
}