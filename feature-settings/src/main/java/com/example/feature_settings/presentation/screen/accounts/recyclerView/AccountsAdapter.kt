package com.example.feature_settings.presentation.screen.accounts.recyclerView

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.common.databinding.ItemButtonWithIconBinding
import com.example.common.domain.models.Account
import com.example.common.presentation.extensions.*

class AccountsAdapter(
    private val onItemClick: (Account) -> Unit
) : RecyclerView.Adapter<AccountsAdapter.AccountsViewHolder>() {

    private var list = mutableListOf<Account>()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<Account>) {
        this.list = list.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountsViewHolder {
        return AccountsViewHolder(
            binding = getBinding(parent),
            onClick = onItemClick
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: AccountsViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    private fun getBinding(parent: ViewGroup): ItemButtonWithIconBinding
        = ItemButtonWithIconBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    class AccountsViewHolder(
        private val binding: ItemButtonWithIconBinding,
        private val onClick: (Account) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(account: Account) {
            with (binding) {
                setStrokeVisibility(true)
                setImage(account.icon.drawableRes)
                setTitle(account.name)
                setValue(
                    account.currentBalance.formatAsBalance(
                        currency = account.currency,
                        withPlus = false
                    )
                )
                setOnClickListener {
                    onClick.invoke(account)
                }
            }
        }
    }
}