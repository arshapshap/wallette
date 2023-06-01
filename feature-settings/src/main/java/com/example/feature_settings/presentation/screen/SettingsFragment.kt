package com.example.feature_settings.presentation.screen

import android.content.res.ColorStateList
import android.util.TypedValue
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.common.di.FeatureUtils
import com.example.common.presentation.base.BaseFragment
import com.example.common.presentation.base.BaseViewModel
import com.example.feature_settings.R
import com.example.feature_settings.di.SettingsComponent
import com.example.feature_settings.databinding.FragmentSettingsBinding
import com.example.feature_settings.databinding.ItemBlackButtonBinding
import com.example.feature_settings.di.SettingsFeatureApi


class SettingsFragment : BaseFragment<SettingsViewModel>(R.layout.fragment_settings) {

    private val binding by viewBinding(FragmentSettingsBinding::bind)
    private val component: SettingsComponent by lazy {
        FeatureUtils.getFeature(this, SettingsFeatureApi::class.java)
    }

    override fun inject() {
        component.inject(this)
    }

    override fun createViewModel(): BaseViewModel {
        return component.settingsViewModel().create()
    }

    override fun initViews() {
        binding.enableSyncLayout.setContent(
            iconRes = R.drawable.ic_sync,
            titleRes = R.string.enable_synchronization,
            colorInt = getColorPrimary()
        ) {

        }

        binding.accountsLayout.setContent(
            iconRes = R.drawable.ic_account,
            titleRes = R.string.all_accounts,
            isRightArrowVisible = true
        ) {

        }

        binding.categoriesLayout.setContent(
            iconRes = R.drawable.ic_category,
            titleRes = R.string.all_categories,
            isRightArrowVisible = true
        ) {

        }

        binding.tagsLayout.setContent(
            iconRes = R.drawable.ic_tag,
            titleRes = R.string.all_tags,
            isRightArrowVisible = true
        ) {

        }

        binding.currencyLayout.setContent(
            iconRes = R.drawable.ic_currency,
            titleRes = R.string.currency,
            value = "RUB",
            isRightArrowVisible = true
        ) {

        }

        binding.languageLayout.setContent(
            iconRes = R.drawable.ic_language,
            titleRes = R.string.language,
            valueRes = R.string.language_ru,
            isRightArrowVisible = true
        ) {

        }

        binding.beginningOfTheWeekLayout.setContent(
            iconRes = R.drawable.ic_calendar_today,
            titleRes = R.string.first_day_of_week,
            valueRes = R.string.monday,
            isRightArrowVisible = true
        ) {

        }

        binding.beginningOfTheMonthLayout.setContent(
            iconRes = R.drawable.ic_calendar_month,
            titleRes = R.string.first_day_of_month,
            value = "1",
            isRightArrowVisible = true
        ) {

        }

        binding.periodSelectionLayout.setContent(
            iconRes = R.drawable.ic_calendar_note,
            titleRes = R.string.period_selection,
            valueRes = R.string.day,
            isRightArrowVisible = true
        ) {

        }

    }

    override fun subscribe() {
        //TODO("Not yet implemented")
    }

    private fun ItemBlackButtonBinding.setContent(
        @DrawableRes iconRes: Int,
        @StringRes titleRes: Int,
        @StringRes valueRes: Int? = null,
        value: String? = null,
        @ColorInt colorInt: Int? = null,
        isRightArrowVisible: Boolean = false,
        onClick: () -> Unit
    ) {
        iconImageView.setImageResource(iconRes)
        titleTextView.setText(titleRes)

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

        binding.root.setOnClickListener {
            onClick.invoke()
        }
    }

    @ColorInt
    private fun getColorPrimary(): Int {
        val typedValue = TypedValue()
        activity?.theme?.resolveAttribute(androidx.appcompat.R.attr.colorPrimary, typedValue, true)
        return typedValue.data
    }
}