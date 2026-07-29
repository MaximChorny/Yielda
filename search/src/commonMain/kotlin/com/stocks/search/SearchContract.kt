package com.stocks.search

data class SearchUiState(
    val query: String = "",
    val isLoading: Boolean = false,
    val results: List<SearchResultItem> = emptyList(),
    val errorMessage: String? = null,
)

data class SearchResultItem(
    val symbol: String,
    val description: String,
    val displaySymbol: String,
    val type: String,
)
