package com.example.feature_settings.presentation.screen.accounts.single

import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.common.di.FeatureUtils
import com.example.common.domain.models.Account
import com.example.common.domain.models.AccountIcon
import com.example.common.presentation.base.BaseFragment
import com.example.common.presentation.base.BaseViewModel
import com.example.feature_settings.R
import com.example.feature_settings.databinding.FragmentSingleAccountBinding
import com.example.feature_settings.di.SettingsComponent
import com.example.feature_settings.di.SettingsFeatureApi
import com.example.feature_settings.presentation.screen.common.IconsAdapter
import com.example.feature_settings.presentation.screen.settings.dialogs.PickerDialogFragment
import com.example.feature_settings.presentation.screen.settings.dialogs.PickerDialogFragment.Companion.PickerType
import com.example.feature_settings.presentation.screen.settings.dialogs.PickerDialogFragment.Companion.showPickerDialog
import com.example.feature_settings.presentation.utils.getColorPrimary
import com.example.feature_settings.presentation.utils.setContent
import com.google.android.flexbox.*

class SingleAccountFragment :
    BaseFragment<SingleAccountViewModel>(R.layout.fragment_single_account), PickerDialogFragment.SelectDialogListener {

    companion object {

        const val ACCOUNT_KEY = "account_key"
    }

    private val binding by viewBinding(FragmentSingleAccountBinding::bind)
    private val component: SettingsComponent by lazy {
        FeatureUtils.getFeature(this, SettingsFeatureApi::class.java)
    }

    override fun inject() {
        component.inject(this)
    }

    @Suppress("DEPRECATION")
    override fun createViewModel(): BaseViewModel {
        return component.singleAccountViewModel()
            .create(arguments?.getSerializable(ACCOUNT_KEY) as? Account)
    }

    override fun initViews() {
        with(binding) {
            binding.currencyLayout.setContent(
                iconRes = R.drawable.ic_currency,
                titleRes = R.string.currency,
                isRightArrowVisible = true,
                isStrokeVisible = true
            )

            accountIconsRecyclerView.adapter = IconsAdapter(
                colorSelected = getColorPrimary()
            ) {
                viewModel.selectIcon(it as AccountIcon)
            }
            accountIconsRecyclerView.layoutManager = getFlexboxLayoutManager()
        }
    }

    override fun subscribe() {
        viewModel.stateLiveData.observe(viewLifecycleOwner) {
            binding.accountNameEditText.setText(it?.account?.name)
            binding.startBalanceEditText.setText(it?.account?.startBalance?.toString() ?: "")
            with ((binding.accountIconsRecyclerView.adapter as IconsAdapter)) {
                setList(it?.icons ?: listOf())

                val iconIndex = it?.icons?.indexOf(it.account.icon)
                if (iconIndex != null) setSelected(iconIndex)
            }

            if (it != null)
                binding.currencyLayout.setContent(
                    value = it.account.currency.name
                ) {
                    showPickerDialog(
                        fragmentManager = childFragmentManager,
                        title = getString(R.string.currency),
                        items = it.availableCurrencies.map { it.name }.toTypedArray(),
                        pickerType = PickerType.Currency
                    )
                }
        }
    }

    override fun onSelected(index: Int, pickerType: PickerType) {
        if (pickerType == PickerType.Currency) {
            val currency = viewModel.stateLiveData.value!!.availableCurrencies[index]
            viewModel.selectCurrency(currency)
        }
    }

    private fun getFlexboxLayoutManager(): FlexboxLayoutManager {
        return FlexboxLayoutManager(context).apply {
            justifyContent = JustifyContent.CENTER
            alignItems = AlignItems.CENTER
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
        }
    }
}