package com.example.feature_statistics_impl.presentation.screen.singleTransaction

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.common.domain.models.Tag
import com.example.feature_statistics_impl.databinding.ItemDropdownTagBinding

class TagsAutocompleteAdapter(
    context: Context,
    private var items: List<Tag>
) : ArrayAdapter<String>(context, 0, items.map { it.name }) {

    private lateinit var binding: ItemDropdownTagBinding

    fun updateSuggestions(newItems: List<Tag>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): String {
        return items[position].toString()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: inflateItemView(parent)
        bindView(position)
        return view
    }

    private fun inflateItemView(parent: ViewGroup): View {
        binding = ItemDropdownTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return binding.root
    }

    private fun bindView(position: Int) {
        val tag = items[position]
        with (binding) {
            nameTextView.text = tag.name
            circleImageView.imageTintList = ColorStateList.valueOf(
                Color.parseColor(tag.color)
            )
        }
    }
}