package com.example.common.presentation.extensions

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.common.R
import com.example.common.databinding.ItemButtonWithIconBinding

fun ItemButtonWithIconBinding.setStrokeVisibility(isStrokeVisible: Boolean) {
    root.setBackgroundResource(R.drawable.bg_button_stroke)
    if (!isStrokeVisible)
        root.backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)
}

fun ItemButtonWithIconBinding.setFillColor(@ColorInt fillColorInt: Int) {
    root.setBackgroundResource(R.drawable.bg_button_fill)
    root.backgroundTintList = ColorStateList.valueOf(fillColorInt)
}

fun ItemButtonWithIconBinding.setRightArrowVisible(isVisible: Boolean) {
    rightArrowImageView.isVisible = isVisible
}

fun ItemButtonWithIconBinding.setColor(@ColorInt colorInt: Int) {
    titleTextView.setTextColor(colorInt)
    valueTextView.setTextColor(colorInt)
    iconImageView.imageTintList = ColorStateList.valueOf(colorInt)
    rightArrowImageView.imageTintList = ColorStateList.valueOf(colorInt)
    root.backgroundTintList = ColorStateList.valueOf(colorInt)
}

fun ItemButtonWithIconBinding.setImage(@DrawableRes drawableRes: Int) {
    iconImageView.setImageResource(drawableRes)
}

fun ItemButtonWithIconBinding.setImageTint(@ColorInt colorInt: Int) {
    iconImageView.imageTintList = ColorStateList.valueOf(colorInt)
}

fun ItemButtonWithIconBinding.setOnClickListener(onClick: (() -> Unit)) {
    linearLayout.setOnClickListener { onClick.invoke() }
}

fun ItemButtonWithIconBinding.setTitle(@StringRes stringRes: Int) {
    titleTextView.setText(stringRes)
}

fun ItemButtonWithIconBinding.setTitle(string: String) {
    titleTextView.text = string
}

fun ItemButtonWithIconBinding.setValue(@StringRes stringRes: Int) {
    valueTextView.setText(stringRes)
}

fun ItemButtonWithIconBinding.setValue(string: String) {
    valueTextView.text = string
}

fun ItemButtonWithIconBinding.invalidate() {
    root.invalidate()
}

fun ItemButtonWithIconBinding.setGone(isGone: Boolean) {
    root.isGone = isGone
}