package com.stocks.search

data class SearchResultItem(
    val symbol: String,
    val description: String,
    val displaySymbol: String,
    val type: String,
    val iconUrl: String = "",
)
