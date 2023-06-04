package com.example.common.presentation.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Date.formatToString(): String {
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)

    val calendar = Calendar.getInstance()
    calendar.time = this
    val groupYear = calendar.get(Calendar.YEAR)

    val outputDateFormatter =
        if (currentYear == groupYear)
            SimpleDateFormat("dd MMM", Locale.getDefault())
        else
            SimpleDateFormat("dd MMM, yyyy", Locale.getDefault())

    return outputDateFormatter.format(this)
}

fun Date.roundToDay(): Date {
    val calendar = Calendar.getInstance().apply {
        time = this@roundToDay
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
    return calendar.time
}