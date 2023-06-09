package com.example.feature_settings.presentation.screen.settings.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

class PickerDialogFragment : DialogFragment() {

    private lateinit var listener: SelectDialogListener

    interface SelectDialogListener {

        fun onSelected(index: Int, pickerType: PickerType)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = parentFragment as SelectDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException((parentFragment.toString() +
                    " must implement SelectDialogListener"))
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val title = requireArguments().getString(TITLE_KEY)
        val items = requireArguments().getStringArray(ITEMS_LIST_KEY) ?: arrayOf()
        val pickerType = requireArguments().getSerializable(PICKER_KEY) as PickerType
        return requireActivity().let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(title)
                .setItems(items) { _, index ->
                    listener.onSelected(index, pickerType)
                }
            builder.create()
        }
    }

    companion object {

        const val ITEMS_LIST_KEY = "items_list"
        const val TITLE_KEY = "title"
        const val PICKER_KEY = "picker_type"

        enum class PickerType {
            Currency,
            Language,
            FirstDayOfWeek,
            FirstDayOfMonth,
            TimePeriod
        }

        fun showPickerDialog(
            fragmentManager: FragmentManager,
            title: String,
            items: Array<String>,
            pickerType: PickerType
        ) {
            val newFragment = PickerDialogFragment()
            newFragment.arguments = Bundle().apply {
                putString(TITLE_KEY, title)
                putStringArray(ITEMS_LIST_KEY, items)
                putSerializable(PICKER_KEY, pickerType)
            }
            newFragment.show(fragmentManager, title)
        }
    }
}