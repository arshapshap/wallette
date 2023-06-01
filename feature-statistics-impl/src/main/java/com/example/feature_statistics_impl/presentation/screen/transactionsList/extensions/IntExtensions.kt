package com.example.feature_statistics_impl.presentation.screen.transactionsList.extensions

import com.example.common.domain.models.Currency
import kotlin.math.absoluteValue

fun Int.getString(currency: Currency): String {
    val stringBuilder = StringBuilder()
    if (this > 0)
        stringBuilder.append("+ ")
    else
        stringBuilder.append("- ")

    val absolute = this.absoluteValue.toString()
    val intLength = absolute.length
    absolute.mapIndexed { index, c ->
        if ((intLength - index) % 3 == 0 && index != 0) {
            stringBuilder.append(" ")
        }
        stringBuilder.append(c)
    }

    stringBuilder.append(" ${currency.symbol}")
    return stringBuilder.toString()
}

fun Int.getTextColor()
    = if (this > 0) com.example.common.R.color.green
    else com.example.common.R.color.red