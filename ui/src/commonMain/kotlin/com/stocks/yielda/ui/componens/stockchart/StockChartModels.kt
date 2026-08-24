package com.stocks.yielda.ui.componens.stockchart

data class PricePoint(
    val timestamp: Long,
    val price: Double,
)

enum class ChartPeriod {
    Day,
    Week,
    Month,
    Year,
    All,
}

sealed interface StockPriceChartState {
    data object Loading : StockPriceChartState
    data object Empty : StockPriceChartState
    data class Error(val message: String) : StockPriceChartState
    data class Content(val points: List<PricePoint>) : StockPriceChartState
}
