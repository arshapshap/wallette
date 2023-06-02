package com.example.feature_settings.presentation.screen.accounts.recyclerView

import androidx.recyclerview.widget.RecyclerView
import com.example.common.domain.models.Account
import com.example.feature_settings.databinding.ItemSettingsElementBinding
import com.example.feature_settings.presentation.utils.setContent
import com.example.feature_statistics_impl.presentation.screen.transactionsList.extensions.formatAsBalance

class AccountsViewHolder(
    private val binding: ItemSettingsElementBinding,
    private val onClick: (Account) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(account: Account) {
        binding.setContent(
            iconRes = account.icon.drawableRes,
            title = account.name,
            value = account.balance.formatAsBalance(
                currency = account.currency,
                withPlus = false
            ),
            isRightArrowVisible = false
        ) {
            onClick.invoke(account)
        }
    }
}