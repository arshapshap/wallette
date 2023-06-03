package com.example.feature_settings.presentation.screen.common

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView
import com.example.common.domain.models.Icon
import com.example.feature_settings.databinding.ItemSelectingIconBinding

class IconsAdapter(
    @ColorInt private val colorSelected: Int,
    private val onItemClick: (Icon) -> Unit,
) : RecyclerView.Adapter<IconsAdapter.IconsViewHolder>() {

    private var list: List<Icon> = listOf()
    private var indexSelected: Int? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<Icon>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun setSelected(index: Int) {
        val old = indexSelected
        indexSelected = index
        notifyItemChanged(index)
        old?.let { notifyItemChanged(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconsViewHolder {
        return IconsViewHolder(
            binding = getBinding(parent),
            onClick = onItemClick,
            colorSelected = colorSelected
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: IconsViewHolder, position: Int) {
        holder.onBind(
            icon = list[position],
            isSelected = position == indexSelected
        )
    }

    private fun getBinding(parent: ViewGroup): ItemSelectingIconBinding
            = ItemSelectingIconBinding.inflate(LayoutInflater.from(parent.context), parent, false)


    class IconsViewHolder(
        private val binding: ItemSelectingIconBinding,
        private val onClick: (Icon) -> Unit,
        @ColorInt private val colorSelected: Int
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(icon: Icon, isSelected: Boolean) {
            with (binding.iconImageView) {
                setImageResource(icon.drawableRes)
                backgroundTintList = if (isSelected) ColorStateList.valueOf(colorSelected)
                    else null
                setOnClickListener {
                    onClick.invoke(icon)
                }
            }
        }
    }
}