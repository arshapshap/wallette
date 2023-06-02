package com.example.feature_statistics_impl.presentation.screen.transactionsList.transactionsRecyclerView

import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.common.domain.models.Transaction
import com.example.feature_statistics_impl.R
import com.example.feature_statistics_impl.databinding.ItemExpandableTransactionBinding
import com.example.feature_statistics_impl.presentation.screen.transactionsList.SortingType
import com.example.feature_statistics_impl.presentation.screen.transactionsList.extensions.getString
import com.example.common.presentation.extensions.formatAsBalance
import com.example.common.presentation.extensions.getColorBySign

class TransactionsViewHolder(
    private val binding: ItemExpandableTransactionBinding,
    private val sortingType: SortingType,
    private val onClick: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(transaction: Transaction) {
        with (binding) {
            arrowImageView.isVisible = transaction.isTransactionGroup

            if (sortingType == SortingType.ByCategory)
                nameTextView.text = transaction.date.getString()
            else
                nameTextView.text = transaction.category?.name ?: binding.root.context.getString(R.string.no_category)
            groupCommentTextView.text = transaction.description

            groupPriceTextView.text = transaction.amount.formatAsBalance(transaction.account.currency)
            groupPriceTextView.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    transaction.amount.getColorBySign()
                )
            )

            binding.root.setOnClickListener {
                onClick.invoke(transaction.id)
            }
        }
    }
}