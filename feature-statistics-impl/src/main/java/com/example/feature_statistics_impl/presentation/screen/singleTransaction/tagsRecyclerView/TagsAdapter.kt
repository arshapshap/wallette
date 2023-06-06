package com.example.feature_statistics_impl.presentation.screen.singleTransaction.tagsRecyclerView

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.common.domain.models.Tag
import com.example.feature_statistics_impl.databinding.ItemTagBinding

class TagsAdapter(
    private val onTagRemoveClick: (Tag) -> Unit
) : RecyclerView.Adapter<TagsAdapter.TagsViewHolder>() {

    private var list: MutableList<Tag> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<Tag>) {
        this.list = list.toMutableList()
        notifyDataSetChanged()
    }

    fun removeItem(index: Int) {
        list.removeAt(index)
        notifyItemRemoved(index)
    }

    fun addItem(tag: Tag) {
        list.add(tag)
        notifyItemInserted(list.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagsViewHolder {
        return TagsViewHolder(
            binding = getBinding(parent),
            onCloseClick = onTagRemoveClick
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: TagsViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    private fun getBinding(parent: ViewGroup): ItemTagBinding
            = ItemTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    class TagsViewHolder(
        private val binding: ItemTagBinding,
        private val onCloseClick: (Tag) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(tag: Tag) {
            with (binding) {
                tagTextView.text = tag.name
                root.backgroundTintList = ColorStateList.valueOf(
                    Color.parseColor(tag.color)
                )
                closeImageView.setOnClickListener {
                    onCloseClick.invoke(tag)
                }
            }
        }
    }
}