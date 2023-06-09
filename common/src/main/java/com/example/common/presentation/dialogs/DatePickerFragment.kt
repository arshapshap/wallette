package com.example.common.presentation.dialogs

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import java.util.*

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var listener: OnSelectDateListener
    private var tag: String? = null

    interface OnSelectDateListener {

        fun onSelect(tag: String?, year: Int, month: Int, day: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = parentFragment as OnSelectDateListener
        } catch (e: ClassCastException) {
            throw ClassCastException((parentFragment.toString() +
                    " must implement ${OnSelectDateListener::class.java}"))
        }
    }

    @Suppress("DEPRECATION")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = requireArguments().getSerializable(CALENDAR_KEY) as Calendar
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        tag = requireArguments().getString(TAG_KEY)

        return DatePickerDialog(requireContext(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        listener.onSelect(tag, year, month, day)
    }

    companion object {

        private const val TAG_KEY = "tag"
        private const val CALENDAR_KEY = "calendar"

        fun showDatePickerDialog(
            fragmentManager: FragmentManager,
            calendar: Calendar,
            tag: String
        ) {
            val newFragment = DatePickerFragment()
            newFragment.arguments = Bundle().apply {
                putString(TAG_KEY, tag)
                putSerializable(CALENDAR_KEY, calendar)
            }
            newFragment.show(fragmentManager, tag)
        }
    }
}