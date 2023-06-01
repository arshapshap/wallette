package com.example.feature_settings.presentation.screen.categories.recyclerView

import androidx.recyclerview.widget.RecyclerView
import com.example.common.domain.models.Category
import com.example.feature_settings.databinding.ItemCategoryBinding

class CategoriesViewHolder(
    private val binding: ItemCategoryBinding,
    private val onClick: (Category) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(category: Category) {
        with (binding) {
            iconImageView.setImageResource(com.example.common.R.drawable.ic_category_cash) // TODO: добавить иконку
            titleTextView.text = category.name
            root.setOnClickListener {
                onClick.invoke(category)
            }
        }
    }
}