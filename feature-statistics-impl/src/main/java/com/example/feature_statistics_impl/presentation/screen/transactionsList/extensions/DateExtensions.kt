package com.example.feature_statistics_impl.presentation.screen.transactionsList.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Date.getString(): String {
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