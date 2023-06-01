package com.example.feature_settings.presentation.utils

import android.content.res.ColorStateList
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import com.example.feature_settings.R
import com.example.feature_settings.databinding.ItemSettingsElementBinding

fun ItemSettingsElementBinding.setContent(
    @DrawableRes iconRes: Int? = null,
    @StringRes titleRes: Int? = null,
    title: String? = null,
    @StringRes valueRes: Int? = null,
    value: String? = null,
    @ColorInt colorInt: Int? = null,
    @ColorInt backgroundColorInt: Int? = null,
    isRightArrowVisible: Boolean? = null,
    onClick: (() -> Unit)? = null
) {
    iconRes?.let { iconImageView.setImageResource(it) }

    titleRes?.let { titleTextView.setText(it) }
    title?.let { titleTextView.text = it }

    valueRes?.let { valueTextView.setText(it) }
    value?.let { valueTextView.text = it }

    isRightArrowVisible?.let { rightArrowImageView.isVisible = it }

    onClick?.let { linearLayout.setOnClickListener { onClick.invoke() } }

    colorInt?.let {
        titleTextView.setTextColor(it)
        valueTextView.setTextColor(it)
        iconImageView.imageTintList = ColorStateList.valueOf(it)
        rightArrowImageView.imageTintList = ColorStateList.valueOf(it)
        root.backgroundTintList = ColorStateList.valueOf(it)
    }
    if (backgroundColorInt == null) {
        root.setBackgroundResource(R.drawable.stroke)
    }
    else {
        root.setBackgroundResource(R.drawable.fill)
        root.backgroundTintList = ColorStateList.valueOf(backgroundColorInt)
    }
}