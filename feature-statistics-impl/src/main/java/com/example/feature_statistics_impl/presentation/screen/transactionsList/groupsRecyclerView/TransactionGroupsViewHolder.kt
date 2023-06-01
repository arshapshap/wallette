package com.example.feature_statistics_impl.presentation.screen.transactionsList.groupsRecyclerView

import android.content.res.ColorStateList
import android.graphics.Color
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.example.common.domain.models.Currency
import com.example.feature_statistics_impl.R
import com.example.feature_statistics_impl.databinding.ItemGroupBinding
import com.example.feature_statistics_impl.presentation.screen.transactionsList.SortingType
import com.example.feature_statistics_impl.presentation.screen.transactionsList.extensions.getString
import com.example.feature_statistics_impl.presentation.screen.transactionsList.extensions.getTextColor
import com.example.feature_statistics_impl.presentation.screen.transactionsList.groupsRecyclerView.transactionGroups.TransactionGroup
import com.example.feature_statistics_impl.presentation.screen.transactionsList.groupsRecyclerView.transactionGroups.TransactionGroupByCategory
import com.example.feature_statistics_impl.presentation.screen.transactionsList.groupsRecyclerView.transactionGroups.TransactionGroupByDate
import com.example.feature_statistics_impl.presentation.screen.transactionsList.groupsRecyclerView.transactionGroups.TransactionGroupByTag
import com.example.feature_statistics_impl.presentation.screen.transactionsList.transactionsRecyclerView.TransactionsAdapter

class TransactionGroupsViewHolder(
    private val binding: ItemGroupBinding,
    private val onTransactionClick: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(group: TransactionGroup, sortingType: SortingType, notifyItemChanged: () -> Unit) {
        with (binding) {
            dateTextView.isGone = true
            categoryIconImageView.isGone = true
            categoryTextView.isGone = true
            tagTextView.isGone = true

            arrowImageView.rotation = 90f
            arrowImageView.rotateArrowWithAnimation(group.isExpanded)

            val groupAmount = group.list.sumOf { it.amount }
            groupAmountTextView.text = groupAmount.getString(Currency.RUB) // TODO: откуда брать валюту??
            groupAmountTextView.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    groupAmount.getTextColor()
                )
            )

            transactionsRecyclerView.isGone = !group.isExpanded
            transactionsRecyclerView.adapter = TransactionsAdapter(
                list = group.list,
                sortingType = sortingType,
                onItemClick = onTransactionClick
            )

            groupHeaderLayout.setOnClickListener {
                group.isExpanded = !group.isExpanded
                arrowImageView.rotateArrowWithAnimation(group.isExpanded)
                notifyItemChanged()
            }
        }
        when (sortingType) {
            SortingType.ByDate -> bindByDate(group as TransactionGroupByDate)
            SortingType.ByCategory -> bindByCategory(group as TransactionGroupByCategory)
            SortingType.ByTag -> bindByTag(group as TransactionGroupByTag)
        }
    }

    private fun bindByDate(group: TransactionGroupByDate) {
        with (binding) {
            dateTextView.isGone = false
            dateTextView.text = group.date.getString()
        }
    }

    private fun bindByCategory(group: TransactionGroupByCategory) {
        with (binding) {
            categoryTextView.isGone = false
            categoryIconImageView.isGone = false
            categoryTextView.text = group.category?.name
                ?: binding.root.context.getString(R.string.no_category)

            if (group.category == null)
                categoryIconImageView.setImageResource(com.example.common.R.drawable.ic_category_empty)
            else
                categoryIconImageView.setImageResource(com.example.common.R.drawable.ic_category_cash)
            //TODO: добавить отображение иконки
        }
    }

    private fun bindByTag(group: TransactionGroupByTag) {
        with (binding) {
            tagTextView.isGone = false
            tagTextView.text = group.tag?.name ?: binding.root.context.getString(R.string.no_tags)

            tagTextView.backgroundTintList = ColorStateList.valueOf(
                Color.parseColor(group.tag?.color ?: "#000000")
            )
            if (group.tag == null)
                tagTextView.setBackgroundResource(R.drawable.bg_tag_dotted)
            else
                tagTextView.setBackgroundResource(R.drawable.bg_tag)
        }
    }

    private fun ImageView.rotateArrowWithAnimation(isExpanded: Boolean) {
        val angle = if (isExpanded) 0F else 180F
        this.animate().rotation(angle).start()
    }
}