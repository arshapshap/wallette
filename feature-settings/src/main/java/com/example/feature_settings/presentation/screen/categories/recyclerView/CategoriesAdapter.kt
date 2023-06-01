package com.example.feature_settings.presentation.screen.categories.recyclerView

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.common.domain.models.Category
import com.example.feature_settings.databinding.ItemCategoryBinding

class CategoriesAdapter(
    private val onItemClick: (Category) -> Unit
) : RecyclerView.Adapter<CategoriesViewHolder>() {

    private var list = mutableListOf<Category>()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<Category>) {
        this.list = list.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        return CategoriesViewHolder(
            binding = getBinding(parent),
            onClick = onItemClick
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    private fun getBinding(parent: ViewGroup): ItemCategoryBinding
        = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
}