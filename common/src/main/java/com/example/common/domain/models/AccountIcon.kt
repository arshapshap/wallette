package com.example.common.domain.models

import androidx.annotation.DrawableRes
import com.example.common.R

enum class AccountIcon(@DrawableRes val drawableRes: Int) {
    Bitcoin(R.drawable.ic_bitcoin),
    Bank(R.drawable.ic_bank),
    Card(R.drawable.ic_card),
    Diamond(R.drawable.ic_diamond),
    Dollar(R.drawable.ic_dollar),
    Empty(R.drawable.ic_empty),
    Euro(R.drawable.ic_euro),
    Lira(R.drawable.ic_lira),
    Monitoring(R.drawable.ic_monitoring),
    Payments(R.drawable.ic_payments),
    Pound(R.drawable.ic_pound),
    Ruble(R.drawable.ic_ruble),
    Savings(R.drawable.ic_savings),
    Wallet(R.drawable.ic_wallet),
    Yuan(R.drawable.ic_yuan)
}