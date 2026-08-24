package com.stocks.search.repo.api

import kotlinx.serialization.Serializable

@Serializable
data class TwelveDataTimeSeriesResponse(
    val status: String = "",
    val values: List<TwelveDataTimeSeriesValueDto> = emptyList(),
    val message: String = "",
)

@Serializable
data class TwelveDataTimeSeriesValueDto(
    val datetime: String = "",
    val close: String = "",
)
