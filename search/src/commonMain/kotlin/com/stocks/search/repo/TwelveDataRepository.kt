package com.stocks.search.repo

import com.stocks.search.StockPricePoint
import com.stocks.search.repo.api.TwelveDataTimeSeriesResponse
import com.stocks.yielda.ui.componens.stockchart.ChartPeriod
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class TwelveDataRepository(
    private val client: HttpClient,
) {
    suspend fun getPricePoints(
        symbol: String,
        period: ChartPeriod,
    ): List<StockPricePoint> {
        val response = client.get("time_series") {
            parameter("symbol", symbol)
            parameter("interval", period.interval)
            parameter("outputsize", period.outputSize)
            parameter("order", "ASC")
        }.body<TwelveDataTimeSeriesResponse>()

        if (response.status == "error") {
            return emptyList()
        }

        return response.values.mapIndexedNotNull { index, value ->
            val closePrice = value.close.toDoubleOrNull() ?: return@mapIndexedNotNull null
            StockPricePoint(
                timestamp = index.toLong(),
                price = closePrice,
            )
        }
    }
}

private val ChartPeriod.interval: String
    get() = when (this) {
        ChartPeriod.Day -> "1h"
        ChartPeriod.Week -> "1day"
        ChartPeriod.Month -> "1day"
        ChartPeriod.Year -> "1month"
        ChartPeriod.All -> "1month"
    }

private val ChartPeriod.outputSize: Int
    get() = when (this) {
        ChartPeriod.Day -> 24
        ChartPeriod.Week -> 7
        ChartPeriod.Month -> 30
        ChartPeriod.Year -> 12
        ChartPeriod.All -> 60
    }
