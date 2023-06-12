package com.example.feature_statistics_impl.presentation.screen.transactionsList.groupsRecyclerView

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.common.domain.models.Transaction
import com.example.feature_statistics_impl.databinding.ItemGroupBinding
import com.example.feature_statistics_impl.presentation.screen.transactionsList.SortingType
import com.example.feature_statistics_impl.domain.models.TransactionGroup

class TransactionGroupsAdapter(
    private val onTransactionClick: (Transaction) -> Unit
) : RecyclerView.Adapter<TransactionGroupsViewHolder>() {

    private var list = mutableListOf<TransactionGroup>()
    private var sortingType = SortingType.ByDate

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<TransactionGroup>, sortingType: SortingType) {
        this.list = list.toMutableList()
        this.sortingType = sortingType
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionGroupsViewHolder {
        return TransactionGroupsViewHolder(
            binding = getBinding(parent),
            onTransactionClick = onTransactionClick
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: TransactionGroupsViewHolder, position: Int) {
        holder.onBind(list[position], sortingType) {
            notifyItemChanged(position)
        }
    }

    private fun getBinding(parent: ViewGroup): ItemGroupBinding
        = ItemGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
}