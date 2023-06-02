package com.example.feature_settings.presentation.screen.tags.recyclerView

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.common.domain.models.Tag
import com.example.feature_settings.R
import com.example.feature_settings.databinding.ItemSettingsElementBinding
import com.example.feature_settings.presentation.utils.setContent

class TagsAdapter(
    private val onItemClick: (Tag) -> Unit
) : RecyclerView.Adapter<TagsAdapter.TagsViewHolder>() {

    private var list = mutableListOf<Tag>()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<Tag>) {
        this.list = list.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagsViewHolder {
        return TagsViewHolder(
            binding = getBinding(parent),
            onClick = onItemClick
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: TagsViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    private fun getBinding(parent: ViewGroup): ItemSettingsElementBinding
        = ItemSettingsElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    class TagsViewHolder(
        private val binding: ItemSettingsElementBinding,
        private val onClick: (Tag) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(tag: Tag) {
            binding.setContent(
                iconRes = R.drawable.ic_circle,
                title = tag.name,
                isStrokeVisible = true,
                isRightArrowVisible = false
            ) {
                onClick.invoke(tag)
            }
            binding.iconImageView.imageTintList = ColorStateList.valueOf(
                Color.parseColor(tag.color)
            )
        }
    }
}