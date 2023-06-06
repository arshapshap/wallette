package com.example.feature_settings.presentation.screen.tags.recyclerView

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.common.databinding.ItemButtonWithIconBinding
import com.example.common.domain.models.Tag
import com.example.common.presentation.extensions.*
import com.example.feature_settings.R

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

    private fun getBinding(parent: ViewGroup): ItemButtonWithIconBinding
        = ItemButtonWithIconBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    class TagsViewHolder(
        private val binding: ItemButtonWithIconBinding,
        private val onClick: (Tag) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(tag: Tag) {
            with (binding) {
                setStrokeVisibility(true)
                setImage(R.drawable.ic_circle)
                setTitle(tag.name)
                setOnClickListener {
                    onClick.invoke(tag)
                }
                setImageTint(Color.parseColor(tag.color))
            }
        }
    }
}