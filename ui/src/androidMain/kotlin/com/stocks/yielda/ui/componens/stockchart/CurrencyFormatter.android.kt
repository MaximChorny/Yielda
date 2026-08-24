package com.stocks.yielda.ui.componens.stockchart

import java.text.NumberFormat
import java.util.Locale

internal actual fun formatChartCurrency(
    value: Double,
    currencySymbol: String,
): String {
    val formatter = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        minimumFractionDigits = 0
        maximumFractionDigits = 2
    }
    return currencySymbol + formatter.format(value)
}
