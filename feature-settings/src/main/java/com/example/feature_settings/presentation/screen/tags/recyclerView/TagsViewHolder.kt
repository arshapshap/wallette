package com.example.feature_settings.presentation.screen.tags.recyclerView

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.example.common.domain.models.Tag
import com.example.feature_settings.databinding.ItemTagBinding

class TagsViewHolder(
    private val binding: ItemTagBinding,
    private val onClick: (Tag) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(tag: Tag) {
        with (binding) {
            titleTextView.text = tag.name
            iconImageView.imageTintList = ColorStateList.valueOf(
                Color.parseColor(tag.color)
            )
            root.setOnClickListener {
                onClick.invoke(tag)
            }
        }
    }
}