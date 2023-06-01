package com.example.feature_settings.presentation.screen.tags.recyclerView

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.common.domain.models.Tag
import com.example.feature_settings.databinding.ItemTagBinding

class TagsAdapter(
    private val onItemClick: (Tag) -> Unit
) : RecyclerView.Adapter<TagsViewHolder>() {

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

    private fun getBinding(parent: ViewGroup): ItemTagBinding
        = ItemTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
}