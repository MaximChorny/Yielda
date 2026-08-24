package com.stocks.yielda.ui.componens.stockchart

import platform.Foundation.NSLocale
import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterDecimalStyle
import platform.Foundation.currentLocale
import platform.Foundation.numberWithDouble

internal actual fun formatChartCurrency(
    value: Double,
    currencySymbol: String,
): String {
    val formatter = NSNumberFormatter().apply {
        numberStyle = NSNumberFormatterDecimalStyle
        locale = NSLocale.currentLocale
        minimumFractionDigits = 0u
        maximumFractionDigits = 2u
    }
    return currencySymbol + (formatter.stringFromNumber(NSNumber.numberWithDouble(value)) ?: value.trimmedDecimal())
}
