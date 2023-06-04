package com.example.common.presentation.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

class PickerFragment : DialogFragment() {

    private lateinit var listener: OnSelectListener

    interface OnSelectListener {

        fun onSelect(tag: String?, index: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = parentFragment as OnSelectListener
        } catch (e: ClassCastException) {
            throw ClassCastException((parentFragment.toString() +
                    " must implement ${OnSelectListener::class.java}"))
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val title = requireArguments().getString(TITLE_KEY)
        val items = requireArguments().getStringArray(ITEMS_LIST_KEY) ?: arrayOf()
        val tag = requireArguments().getString(TAG_KEY)
        return requireActivity().let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(title)
                .setItems(items) { _, index ->
                    listener.onSelect(tag, index)
                }
            builder.create()
        }
    }

    companion object {

        private const val ITEMS_LIST_KEY = "items_list"
        private const val TITLE_KEY = "title"
        private const val TAG_KEY = "tag"

        fun showPickerDialog(
            fragmentManager: FragmentManager,
            title: String,
            items: Array<String>,
            tag: String
        ) {
            val newFragment = PickerFragment()
            newFragment.arguments = Bundle().apply {
                putString(TITLE_KEY, title)
                putStringArray(ITEMS_LIST_KEY, items)
                putString(TAG_KEY, tag)
            }
            newFragment.show(fragmentManager, tag)
        }
    }
}