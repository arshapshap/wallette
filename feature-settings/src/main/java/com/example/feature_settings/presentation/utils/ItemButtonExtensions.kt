package com.example.feature_settings.presentation.utils

import android.content.res.ColorStateList
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import com.example.feature_settings.databinding.ItemButtonBinding

fun ItemButtonBinding.setContent(
    @DrawableRes iconRes: Int? = null,
    @StringRes titleRes: Int? = null,
    @StringRes valueRes: Int? = null,
    value: String? = null,
    @ColorInt colorInt: Int? = null,
    isRightArrowVisible: Boolean? = null,
    onClick: (() -> Unit)? = null
) {
    if (iconRes != null)
        iconImageView.setImageResource(iconRes)
    if (titleRes != null)
        titleTextView.setText(titleRes)
    if (isRightArrowVisible != null)
        rightArrowImageView.isVisible = isRightArrowVisible
    if (colorInt != null) {
        titleTextView.setTextColor(colorInt)
        iconImageView.imageTintList = ColorStateList.valueOf(colorInt)
        this.root.backgroundTintList = ColorStateList.valueOf(colorInt)
    }
    if (valueRes != null)
        valueTextView.setText(valueRes)
    if (value != null)
        valueTextView.text = value
    if (onClick != null)
        linearLayout.setOnClickListener {
            onClick.invoke()
        }
}