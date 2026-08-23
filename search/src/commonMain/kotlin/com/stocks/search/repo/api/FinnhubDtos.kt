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

@Serializable
data class FinnhubCompanyProfileDto(
    val country: String = "",
    val currency: String = "",
    val estimateCurrency: String = "",
    val exchange: String = "",
    val finnhubIndustry: String = "",
    val floatingShare: Double = 0.0,
    val ipo: String = "",
    val logo: String = "",
    val marketCapitalization: Double = 0.0,
    val name: String = "",
    val phone: String = "",
    val shareOutstanding: Double = 0.0,
    val ticker: String = "",
    val weburl: String = "",
)
