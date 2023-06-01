package com.example.feature_settings.presentation.screen.settings.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class PickerDialogFragment : DialogFragment() {

    private lateinit var listener: SelectDialogListener

    interface SelectDialogListener {

        fun onCurrencySelected(index: Int)

        fun onLanguageSelected(index: Int)

        fun onFirstDayOfWeekSelected(index: Int)

        fun onFirstDayOfMonthSelected(index: Int)

        fun onTimePeriodSelected(index: Int)
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
        val title = requireArguments().getString(TITLE_TAG)
        val items = requireArguments().getStringArray(ITEMS_LIST_TAG) ?: arrayOf()
        val pickerType = requireArguments().getSerializable(PICKER_TYPE) as PickerType
        return requireActivity().let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(title)
                .setItems(items) { _, index ->
                    when (pickerType) {
                        PickerType.Currency -> listener.onCurrencySelected(index)
                        PickerType.Language -> listener.onLanguageSelected(index)
                        PickerType.FirstDayOfWeek -> listener.onFirstDayOfWeekSelected(index)
                        PickerType.FirstDayOfMonth -> listener.onFirstDayOfMonthSelected(index)
                        PickerType.TimePeriod -> listener.onTimePeriodSelected(index)
                    }
                }
            builder.create()
        }
    }

    companion object {

        const val ITEMS_LIST_TAG = "items_list"
        const val TITLE_TAG = "title"
        const val PICKER_TYPE = "picker_type"
    }
}