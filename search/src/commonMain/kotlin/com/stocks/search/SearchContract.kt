package com.stocks.search

data class SearchResultItem(
    val symbol: String,
    val description: String,
    val displaySymbol: String,
    val type: String,
    val iconUrl: String = "",
)

data class StockQuote(
    val currentPrice: Double,
    val change: Double,
    val percentChange: Double,
    val highPriceOfTheDay: Double,
    val lowPriceOfTheDay: Double,
    val openPriceOfTheDay: Double,
    val previousClosePrice: Double,
)
