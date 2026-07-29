package com.stocks.search.repo.api

import kotlinx.serialization.Serializable

@Serializable
data class FinnhubSearchResponse(
    val count: Int = 0,
    val result: List<FinnhubSearchResultDto> = emptyList(),
)

@Serializable
data class FinnhubSearchResultDto(
    val description: String = "",
    val displaySymbol: String = "",
    val symbol: String = "",
    val type: String = "",
)
