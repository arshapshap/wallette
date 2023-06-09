package com.example.wallette.presentation.extensions

import android.annotation.SuppressLint
import com.google.android.material.floatingactionbutton.FloatingActionButton

@SuppressLint("ResourceType")
fun FloatingActionButton.applyStyle(styleResId: Int) {
    if (context != null) {
        val typedArray = context.obtainStyledAttributes(styleResId, intArrayOf(
            androidx.appcompat.R.attr.backgroundTint,
            androidx.appcompat.R.attr.tint
        ))
        val backgroundTint = typedArray.getColorStateList(0)
        val tint = typedArray.getColorStateList(1)

        backgroundTint?.let { backgroundTintList = it }
        tint?.let { imageTintList = it }

        typedArray.recycle()
    }
}