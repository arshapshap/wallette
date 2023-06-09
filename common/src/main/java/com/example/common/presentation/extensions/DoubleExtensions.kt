package com.example.common.presentation.extensions

import com.example.common.domain.models.Currency
import java.text.DecimalFormat
import kotlin.math.abs

fun Double.formatAsBalance(currency: Currency, withPlus: Boolean = true): String {
    val sign = if (this >= 0 && withPlus) "+" else if (this >= 0) "" else "-"
    val balance = DecimalFormat("#,##0.00").format(abs(this))
    return "$sign $balance ${currency.symbol}"
}

fun Double.getColorBySign()
    = if (this > 0) com.example.common.R.color.green
    else com.example.common.R.color.red