package com.example.feature_settings.presentation.screen.singleAccount

import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.common.di.FeatureUtils
import com.example.common.domain.models.Account
import com.example.common.domain.models.enums.AccountIcon
import com.example.common.presentation.base.BaseFragment
import com.example.common.presentation.base.BaseViewModel
import com.example.common.presentation.extensions.*
import com.example.feature_settings.R
import com.example.feature_settings.databinding.FragmentSingleAccountBinding
import com.example.feature_settings.di.SettingsComponent
import com.example.feature_settings.di.SettingsFeatureApi
import com.example.feature_settings.presentation.screen.common.IconsAdapter
import com.example.common.presentation.dialogs.PickerFragment
import com.example.common.presentation.dialogs.PickerFragment.Companion.showPickerDialog
import com.example.common.presentation.extensions.getColorPrimary
import com.example.common.presentation.floatingButtonInterfaces.FloatingButtonListenersManager
import com.example.common.presentation.floatingButtonInterfaces.OnFloatingButtonClickListener
import com.google.android.flexbox.*

class SingleAccountFragment :
    BaseFragment<SingleAccountViewModel>(R.layout.fragment_single_account), PickerFragment.OnSelectListener, OnFloatingButtonClickListener {

    companion object {

        const val ACCOUNT_KEY = "account_key"
        private const val CURRENCY_PICKER_TAG = "currency"
    }

    private val binding by viewBinding(FragmentSingleAccountBinding::bind)
    private val component: SettingsComponent by lazy {
        FeatureUtils.getFeature(this, SettingsFeatureApi::class.java)
    }

    override fun inject() {
        component.inject(this)
    }

    override fun onStart() {
        super.onStart()
        (requireActivity() as? FloatingButtonListenersManager)?.subscribeOnFloatingButtonClick(this)
    }

    override fun onStop() {
        super.onStop()
        (requireActivity() as? FloatingButtonListenersManager)?.setDefaultOnFloatingButtonClickListener()
    }

    @Suppress("DEPRECATION")
    override fun createViewModel(): BaseViewModel {
        return component.singleAccountViewModel()
            .create(arguments?.getSerializable(ACCOUNT_KEY) as? Account)
    }

    override fun initViews() {
        with (binding.currencyLayout) {
            setStrokeVisibility(false)
            setRightArrowVisible(true)
            setImage(R.drawable.ic_currency)
            setTitle(R.string.currency)
        }

        with (binding.accountIconsRecyclerView) {
            adapter = IconsAdapter(colorSelected = getColorPrimary()) {
                viewModel.selectIcon(it as AccountIcon)
            }
            layoutManager = getFlexboxLayoutManager()
        }

        with (binding) {
            accountNameEditText.doAfterTextChanged {
                viewModel.editName(it.toString())
            }
            startBalanceEditText.doAfterTextChanged {
                viewModel.editStartBalance(it.toString())
            }
        }

    }

    override fun subscribe() {
        viewModel.startLiveData.observe(viewLifecycleOwner) {
            with (binding) {
                accountNameEditText.setText(it?.account?.name)
                startBalanceEditText.setText(it?.account?.startBalance?.toString() ?: "")

                if (it.canBeDeleted) {
                    deleteImageButton.isVisible = true
                    deleteImageButton.setOnClickListener {
                        viewModel.delete()
                    }
                }

                getIconsAdapter()?.setList(it?.icons ?: listOf())
            }
        }
        viewModel.editingAccountLiveData.observe(viewLifecycleOwner) {
            with (binding) {
                val iconIndex = viewModel.startLiveData.value?.icons?.indexOf(it.icon)
                if (iconIndex != null) {
                    getIconsAdapter()?.setSelected(iconIndex)
                    accountIconsRecyclerView.scrollToPosition(iconIndex)
                }

                if (it != null) {
                    currencyLayout.setValue(it.currency.name)
                    currencyLayout.setOnClickListener {
                        showPickerDialog(
                            fragmentManager = childFragmentManager,
                            title = getString(R.string.currency),
                            items = viewModel.startLiveData.value?.availableCurrencies?.map { it.name }?.toTypedArray() ?: arrayOf(),
                            tag = CURRENCY_PICKER_TAG
                        )
                    }
                }
            }
        }
    }

    override fun onSelect(tag: String?, index: Int) {
        if (tag == CURRENCY_PICKER_TAG) {
            val currency = viewModel.startLiveData.value!!.availableCurrencies[index]
            viewModel.selectCurrency(currency)
        }
    }

    override fun onFloatingButtonClick() {
        viewModel.save()
    }

    private fun getIconsAdapter()
            = (binding.accountIconsRecyclerView.adapter as? IconsAdapter)

    private fun getFlexboxLayoutManager(): FlexboxLayoutManager {
        return FlexboxLayoutManager(context).apply {
            justifyContent = JustifyContent.CENTER
            alignItems = AlignItems.CENTER
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
        }
    }
}