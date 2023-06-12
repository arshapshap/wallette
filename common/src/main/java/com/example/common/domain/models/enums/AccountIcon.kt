package com.example.common.domain.models.enums

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import com.example.common.R

enum class AccountIcon(
    @DrawableRes override val drawableRes: Int,
    @ColorInt override val colorInt: Int
) : Icon {
    Bitcoin(R.drawable.ic_bitcoin, Color.parseColor("#FF9800")),
    Car(R.drawable.ic_car, Color.parseColor("#03A9F4")),
    Card(R.drawable.ic_card, Color.parseColor("#FF8E7EF0")),
    Diamond(R.drawable.ic_diamond, Color.parseColor("#03A9F4")),
    Dollar(R.drawable.ic_dollar, Color.parseColor("#4CAF50")),
    Empty(R.drawable.ic_empty, Color.parseColor("#00000000")),
    Euro(R.drawable.ic_euro, Color.parseColor("#FFEB3B")),
    Lira(R.drawable.ic_lira, Color.parseColor("#F44336")),
    Monitoring(R.drawable.ic_monitoring, Color.parseColor("#FFE2DA1C")),
    Payments(R.drawable.ic_payments, Color.parseColor("#FF09CC3F")),
    Pound(R.drawable.ic_pound, Color.parseColor("#FF29FCBC")),
    Ruble(R.drawable.ic_ruble, Color.parseColor("#E4BE35")),
    Savings(R.drawable.ic_savings, Color.parseColor("#9F177C")),
    Wallet(R.drawable.ic_wallet, Color.parseColor("#FF7F4688")),
    Yuan(R.drawable.ic_yuan, Color.parseColor("#E91E63"))
}