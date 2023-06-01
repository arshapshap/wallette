package com.example.feature_settings.presentation.screen.accounts

import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.common.di.FeatureUtils
import com.example.common.presentation.base.BaseFragment
import com.example.common.presentation.base.BaseViewModel
import com.example.feature_settings.R
import com.example.feature_settings.databinding.FragmentAccountsBinding
import com.example.feature_settings.di.SettingsComponent
import com.example.feature_settings.di.SettingsFeatureApi
import com.example.feature_settings.presentation.screen.accounts.recyclerView.AccountsAdapter
import com.example.feature_settings.presentation.utils.getColorPrimary
import com.example.feature_settings.presentation.utils.setContent

class AccountsFragment : BaseFragment<AccountsViewModel>(R.layout.fragment_accounts) {

    private val binding by viewBinding(FragmentAccountsBinding::bind)
    private val component: SettingsComponent by lazy {
        FeatureUtils.getFeature(this, SettingsFeatureApi::class.java)
    }

    override fun inject() {
        component.inject(this)
    }

    override fun createViewModel(): BaseViewModel {
        return component.accountsViewModel().create()
    }

    override fun initViews() {
        with (binding) {
            addAccountLayout.setContent(
                iconRes = R.drawable.ic_plus,
                titleRes = R.string.add_account,
                colorInt = getColorPrimary()
            ) {
                viewModel.openAccountCreating()
            }

            listRecyclerView.adapter = AccountsAdapter {
                viewModel.openAccount(it)
            }

            addMoneyTransferLayout.setContent(
                iconRes = R.drawable.ic_transfer,
                titleRes = R.string.transfer_money,
                colorInt = getColorPrimary()
            ) {
                viewModel.openTransferCreating()
            }
        }
    }

    override fun subscribe() {
        viewModel.listLiveData.observe(viewLifecycleOwner) {
            (binding.listRecyclerView.adapter as AccountsAdapter).setList(it)
        }
    }
}