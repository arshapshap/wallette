package com.example.feature_settings.presentation.utils

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.feature_settings.R
import com.example.feature_settings.databinding.ItemSettingsElementBinding

fun ItemSettingsElementBinding.setStrokeVisibility(isStrokeVisible: Boolean) {
    root.setBackgroundResource(R.drawable.bg_button_stroke)
    if (!isStrokeVisible)
        root.backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)
}

fun ItemSettingsElementBinding.setFillColor(@ColorInt fillColorInt: Int) {
    root.setBackgroundResource(R.drawable.bg_button_fill)
    root.backgroundTintList = ColorStateList.valueOf(fillColorInt)
}

fun ItemSettingsElementBinding.setRightArrowVisible(isVisible: Boolean) {
    rightArrowImageView.isVisible = isVisible
}

fun ItemSettingsElementBinding.setColor(@ColorInt colorInt: Int) {
    titleTextView.setTextColor(colorInt)
    valueTextView.setTextColor(colorInt)
    iconImageView.imageTintList = ColorStateList.valueOf(colorInt)
    rightArrowImageView.imageTintList = ColorStateList.valueOf(colorInt)
    root.backgroundTintList = ColorStateList.valueOf(colorInt)
}

fun ItemSettingsElementBinding.setImage(@DrawableRes drawableRes: Int) {
    iconImageView.setImageResource(drawableRes)
}

fun ItemSettingsElementBinding.setImageTint(@ColorInt colorInt: Int) {
    iconImageView.imageTintList = ColorStateList.valueOf(colorInt)
}

fun ItemSettingsElementBinding.setOnClickListener(onClick: (() -> Unit)) {
    linearLayout.setOnClickListener { onClick.invoke() }
}

fun ItemSettingsElementBinding.setTitle(@StringRes stringRes: Int) {
    titleTextView.setText(stringRes)
}

fun ItemSettingsElementBinding.setTitle(string: String) {
    titleTextView.text = string
}

fun ItemSettingsElementBinding.setValue(@StringRes stringRes: Int) {
    valueTextView.setText(stringRes)
}

fun ItemSettingsElementBinding.setValue(string: String) {
    valueTextView.text = string
}

fun ItemSettingsElementBinding.invalidate() {
    root.invalidate()
}

fun ItemSettingsElementBinding.setGone(isGone: Boolean) {
    root.isGone = isGone
}