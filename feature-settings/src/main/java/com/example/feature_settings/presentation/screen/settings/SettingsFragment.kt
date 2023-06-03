package com.example.feature_settings.presentation.screen.settings

import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.common.di.FeatureUtils
import com.example.common.presentation.base.BaseFragment
import com.example.common.presentation.base.BaseViewModel
import com.example.feature_settings.R
import com.example.feature_settings.databinding.FragmentSettingsBinding
import com.example.feature_settings.di.SettingsComponent
import com.example.feature_settings.di.SettingsFeatureApi
import com.example.feature_settings.presentation.screen.settings.dialogs.PickerDialogFragment
import com.example.feature_settings.presentation.screen.settings.dialogs.PickerDialogFragment.Companion.PickerType
import com.example.feature_settings.presentation.screen.settings.dialogs.PickerDialogFragment.Companion.showPickerDialog
import com.example.feature_settings.presentation.utils.*


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
        with (binding.enableSyncLayout) {
            setColor(getColorOnPrimary())
            setFillColor(getColorPrimary())
            setImage(R.drawable.ic_sync)
            setTitle(R.string.enable_synchronization)
            setOnClickListener {
                viewModel.enableSynchronization()
            }
        }

        with (binding.accountsLayout) {
            setRightArrowVisible(true)
            setImage(R.drawable.ic_account)
            setTitle(R.string.accounts_fragment_name)
            setOnClickListener {
                viewModel.openAccounts()
            }
        }

        with (binding.categoriesLayout) {
            setRightArrowVisible(true)
            setImage(R.drawable.ic_category)
            setTitle(R.string.categories_fragment_name)
            setOnClickListener {
                viewModel.openCategories()
            }
        }

        with (binding.tagsLayout) {
            setRightArrowVisible(true)
            setImage(R.drawable.ic_tag)
            setTitle(R.string.tags_fragment_name)
            setOnClickListener {
                viewModel.openTags()
            }
        }

        with (binding.currencyLayout) {
            setRightArrowVisible(true)
            setImage(R.drawable.ic_currency)
            setTitle(R.string.main_currency)
        }

        with (binding.languageLayout) {
            setRightArrowVisible(true)
            setImage(R.drawable.ic_language)
            setTitle(R.string.language)
        }

        with (binding.firstDayOfWeekLayout) {
            setRightArrowVisible(true)
            setImage(R.drawable.ic_calendar_today)
            setTitle(R.string.first_day_of_week)
        }

        with (binding.firstDayOfMonthLayout) {
            setRightArrowVisible(true)
            setImage(R.drawable.ic_calendar_month)
            setTitle(R.string.first_day_of_month)
        }

        with (binding.timePeriodLayout) {
            setRightArrowVisible(true)
            setImage(R.drawable.ic_calendar_note)
            setTitle(R.string.time_period)
        }
    }

    override fun subscribe() {
        with (viewModel) {
            isSynchronized.observe(viewLifecycleOwner) {
                with (binding.enableSyncLayout) {
                    invalidate()
                    setImage(R.drawable.ic_sync)
                }

                if (it)
                    with (binding.enableSyncLayout) {
                        setColor(getColorPrimary())
                        setStrokeVisibility(true)
                        setTitle(R.string.disable_synchronization)
                        setOnClickListener {
                            viewModel.disableSynchronization()
                        }
                    }
                else
                    with (binding.enableSyncLayout) {
                        setColor(getColorOnPrimary())
                        setFillColor(getColorPrimary())
                        setTitle(R.string.enable_synchronization)
                        setOnClickListener {
                            viewModel.enableSynchronization()
                        }
                    }
            }

            settingsLiveData.observe(viewLifecycleOwner) {
                with (binding.currencyLayout) {
                    setValue(it.currency.name)
                    setOnClickListener {
                        showPickerDialog(
                            fragmentManager = childFragmentManager,
                            title = getString(R.string.main_currency),
                            items = it.availableCurrencies.map { it.name }.toTypedArray(),
                            pickerType = PickerType.Currency
                        )
                    }
                }

                with (binding.languageLayout) {
                    setValue(it.language.stringRes)
                    setOnClickListener {
                        showPickerDialog(
                            fragmentManager = childFragmentManager,
                            title = getString(R.string.language),
                            items = it.availableLanguages.map { getString(it.stringRes) }.toTypedArray(),
                            pickerType = PickerType.Language
                        )
                    }
                }

                with (binding.firstDayOfWeekLayout) {
                    setValue(it.firstDayOfWeek.stringRes)
                    setOnClickListener {
                        showPickerDialog(
                            fragmentManager = childFragmentManager,
                            title = getString(R.string.first_day_of_week),
                            items = it.daysOfWeek.map { getString(it.stringRes) }.toTypedArray(),
                            pickerType = PickerType.FirstDayOfWeek
                        )
                    }
                }

                with (binding.firstDayOfMonthLayout) {
                    setValue(it.firstDayOfMonth.toString())
                    setOnClickListener {
                        showPickerDialog(
                            fragmentManager = childFragmentManager,
                            title = getString(R.string.first_day_of_month),
                            items = it.daysOfMonth.map { it.toString() }.toTypedArray(),
                            pickerType = PickerType.FirstDayOfMonth
                        )
                    }
                }

                with (binding.timePeriodLayout) {
                    setValue(it.timePeriod.stringRes)
                    setOnClickListener {
                        showPickerDialog(
                            fragmentManager = childFragmentManager,
                            title = getString(R.string.time_period),
                            items = it.availableTimePeriods.map { getString(it.stringRes) }.toTypedArray(),
                            pickerType = PickerType.TimePeriod
                        )
                    }
                }
            }
        }
    }

    override fun onSelected(index: Int, pickerType: PickerType) {
        when (pickerType) {
            PickerType.Currency -> onCurrencySelected(index)
            PickerType.Language -> onLanguageSelected(index)
            PickerType.FirstDayOfWeek -> onFirstDayOfWeekSelected(index)
            PickerType.FirstDayOfMonth -> onFirstDayOfMonthSelected(index)
            PickerType.TimePeriod -> onTimePeriodSelected(index)
        }
    }

    private fun onCurrencySelected(index: Int) {
        val currency = viewModel.settingsLiveData.value!!.availableCurrencies[index]
        viewModel.selectCurrency(currency)
    }

    private fun onLanguageSelected(index: Int) {
        val language = viewModel.settingsLiveData.value!!.availableLanguages[index]
        viewModel.selectLanguage(language)
    }

    private fun onFirstDayOfWeekSelected(index: Int) {
        val firstDayOfWeek = viewModel.settingsLiveData.value!!.daysOfWeek[index]
        viewModel.selectFirstDayOfWeek(firstDayOfWeek)
    }

    private fun onFirstDayOfMonthSelected(index: Int) {
        val firstDayOfMonth = viewModel.settingsLiveData.value!!.daysOfMonth[index]
        viewModel.selectFirstDayOfMonth(firstDayOfMonth)
    }

    private fun onTimePeriodSelected(index: Int) {
        val timePeriod = viewModel.settingsLiveData.value!!.availableTimePeriods[index]
        viewModel.selectTimePeriod(timePeriod)
    }
}