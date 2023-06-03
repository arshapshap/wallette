package com.example.feature_statistics_impl.presentation.screen.transactionsList.transactionsRecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.common.domain.models.Transaction
import com.example.feature_statistics_impl.databinding.ItemExpandableTransactionBinding
import com.example.feature_statistics_impl.presentation.screen.transactionsList.SortingType

class TransactionsAdapter(
    private val list: List<Transaction>,
    private val sortingType: SortingType,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<TransactionsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionsViewHolder {
        return TransactionsViewHolder(
            binding = getBinding(parent),
            sortingType = sortingType,
            onClick = onItemClick
        )
    }

    override fun getItemCount(): Int
        = list.size

    override fun onBindViewHolder(holder: TransactionsViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    private fun getBinding(parent: ViewGroup)
        = ItemExpandableTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
}