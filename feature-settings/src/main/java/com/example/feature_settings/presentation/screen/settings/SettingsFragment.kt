package com.example.feature_settings.presentation.screen.settings

import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.common.di.FeatureUtils
import com.example.common.presentation.base.BaseFragment
import com.example.common.presentation.base.BaseViewModel
import com.example.feature_settings.R
import com.example.feature_settings.databinding.FragmentSettingsBinding
import com.example.feature_settings.di.SettingsComponent
import com.example.feature_settings.di.SettingsFeatureApi
import com.example.feature_settings.presentation.screen.settings.dialogs.PickerDialogFragment
import com.example.feature_settings.presentation.screen.settings.dialogs.PickerType
import com.example.feature_settings.presentation.utils.getColorPrimary
import com.example.feature_settings.presentation.utils.getTextColor
import com.example.feature_settings.presentation.utils.setContent


class SettingsFragment : BaseFragment<SettingsViewModel>(R.layout.fragment_settings),
    PickerDialogFragment.SelectDialogListener {

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
            colorInt = getTextColor(),
            backgroundColorInt = getColorPrimary(),
            isRightArrowVisible = false
        ) {
            viewModel.enableSynchronization()
        }

        binding.accountsLayout.setContent(
            iconRes = R.drawable.ic_account,
            titleRes = R.string.accounts_fragment_name,
            isRightArrowVisible = true
        ) {
            viewModel.openAccounts()
        }

        binding.categoriesLayout.setContent(
            iconRes = R.drawable.ic_category,
            titleRes = R.string.categories_fragment_name,
            isRightArrowVisible = true
        ) {
            viewModel.openCategories()
        }

        binding.tagsLayout.setContent(
            iconRes = R.drawable.ic_tag,
            titleRes = R.string.tags_fragment_name,
            isRightArrowVisible = true
        ) {
            viewModel.openTags()
        }

        binding.currencyLayout.setContent(
            iconRes = R.drawable.ic_currency,
            titleRes = R.string.currency,
            isRightArrowVisible = true
        )

        binding.languageLayout.setContent(
            iconRes = R.drawable.ic_language,
            titleRes = R.string.language,
            isRightArrowVisible = true
        )

        binding.firstDayOfWeekLayout.setContent(
            iconRes = R.drawable.ic_calendar_today,
            titleRes = R.string.first_day_of_week,
            isRightArrowVisible = true
        )

        binding.firstDayOfMonthLayout.setContent(
            iconRes = R.drawable.ic_calendar_month,
            titleRes = R.string.first_day_of_month,
            isRightArrowVisible = true
        )

        binding.timePeriodLayout.setContent(
            iconRes = R.drawable.ic_calendar_note,
            titleRes = R.string.time_period,
            isRightArrowVisible = true
        )

    }

    override fun subscribe() {
        with (viewModel) {
            isSynchronized.observe(viewLifecycleOwner) {
                binding.enableSyncLayout.root.invalidate()
                if (it)
                    binding.enableSyncLayout.setContent(
                        iconRes = R.drawable.ic_sync,
                        titleRes = R.string.disable_synchronization,
                        colorInt = getColorPrimary(),
                        isRightArrowVisible = false
                    ) {
                        viewModel.enableSynchronization()
                    }
                else
                    binding.enableSyncLayout.setContent(
                        iconRes = R.drawable.ic_sync,
                        titleRes = R.string.enable_synchronization,
                        colorInt = getTextColor(),
                        backgroundColorInt = getColorPrimary(),
                        isRightArrowVisible = false
                    ) {
                        viewModel.enableSynchronization()
                    }
            }

            dataLiveData.observe(viewLifecycleOwner) {
                binding.currencyLayout.setContent(
                    value = it.currency.name
                ) {
                    showPickerDialog(
                        title = getString(R.string.currency),
                        items = it.availableCurrencies.map { it.name }.toTypedArray(),
                        pickerType = PickerType.Currency
                    )
                }

                binding.languageLayout.setContent(
                    valueRes = it.language.stringRes
                ) {
                    showPickerDialog(
                        title = getString(R.string.language),
                        items = it.availableLanguages.map { getString(it.stringRes) }.toTypedArray(),
                        pickerType = PickerType.Language
                    )
                }

                binding.firstDayOfWeekLayout.setContent(
                    valueRes = it.firstDayOfWeek.stringRes
                ) {
                    showPickerDialog(
                        title = getString(R.string.first_day_of_week),
                        items = it.daysOfWeek.map { getString(it.stringRes) }.toTypedArray(),
                        pickerType = PickerType.FirstDayOfWeek
                    )
                }

                binding.firstDayOfMonthLayout.setContent(
                    value = it.firstDayOfMonth.toString()
                ) {
                    showPickerDialog(
                        title = getString(R.string.first_day_of_month),
                        items = it.daysOfMonth.map { it.toString() }.toTypedArray(),
                        pickerType = PickerType.FirstDayOfMonth
                    )
                }

                binding.timePeriodLayout.setContent(
                    valueRes = it.timePeriod.stringRes
                ) {
                    showPickerDialog(
                        title = getString(R.string.time_period),
                        items = it.availableTimePeriods.map { getString(it.stringRes) }.toTypedArray(),
                        pickerType = PickerType.TimePeriod
                    )
                }
            }
        }
    }

    private fun showPickerDialog(
        title: String,
        items: Array<String>,
        pickerType: PickerType
    ) {
        val newFragment = PickerDialogFragment()
        newFragment.arguments = Bundle().apply {
            putString(PickerDialogFragment.TITLE_TAG, title)
            putStringArray(PickerDialogFragment.ITEMS_LIST_TAG, items)
            putSerializable(PickerDialogFragment.PICKER_TYPE, pickerType)
        }
        newFragment.show(childFragmentManager, title)
    }

    override fun onCurrencySelected(index: Int) {
        val currency = viewModel.dataLiveData.value!!.availableCurrencies[index]
        viewModel.selectCurrency(currency)
    }

    override fun onLanguageSelected(index: Int) {
        val language = viewModel.dataLiveData.value!!.availableLanguages[index]
        viewModel.selectLanguage(language)
    }

    override fun onFirstDayOfWeekSelected(index: Int) {
        val firstDayOfWeek = viewModel.dataLiveData.value!!.daysOfWeek[index]
        viewModel.selectFirstDayOfWeek(firstDayOfWeek)
    }

    override fun onFirstDayOfMonthSelected(index: Int) {
        val firstDayOfMonth = viewModel.dataLiveData.value!!.daysOfMonth[index]
        viewModel.selectFirstDayOfMonth(firstDayOfMonth)
    }

    override fun onTimePeriodSelected(index: Int) {
        val timePeriod = viewModel.dataLiveData.value!!.availableTimePeriods[index]
        viewModel.selectTimePeriod(timePeriod)
    }
}