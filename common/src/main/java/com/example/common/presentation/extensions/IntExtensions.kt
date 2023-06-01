package com.example.feature_statistics_impl.presentation.screen.transactionsList.extensions

import com.example.common.domain.models.Currency
import kotlin.math.abs

fun Int.formatAsBalance(currency: Currency, withPlus: Boolean = true): String {
    val sign = if (this >= 0 && withPlus) "+" else if (this >= 0) "" else "-"
    val balance = abs(this).toString().reversed().chunked(3).joinToString(" ").reversed()
    return "$sign $balance ${currency.symbol}"
}

fun Int.getColorBySign()
    = if (this > 0) com.example.common.R.color.green
    else com.example.common.R.color.red