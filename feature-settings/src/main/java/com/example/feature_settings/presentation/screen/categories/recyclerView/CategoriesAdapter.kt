package com.example.feature_settings.presentation.screen.categories.recyclerView

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.common.domain.models.Category
import com.example.feature_settings.databinding.ItemSettingsElementBinding
import com.example.feature_settings.presentation.utils.setStrokeVisibility
import com.example.feature_settings.presentation.utils.setImage
import com.example.feature_settings.presentation.utils.setOnClickListener
import com.example.feature_settings.presentation.utils.setTitle

class CategoriesAdapter(
    private val onItemClick: (Category) -> Unit
) : RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

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

    private fun getBinding(parent: ViewGroup): ItemSettingsElementBinding
        = ItemSettingsElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    class CategoriesViewHolder(
        private val binding: ItemSettingsElementBinding,
        private val onClick: (Category) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(category: Category) {
            with (binding) {
                setStrokeVisibility(true)
                setImage(category.icon.drawableRes)
                setTitle(category.name)
                setOnClickListener {
                    onClick.invoke(category)
                }
            }
        }
    }
}