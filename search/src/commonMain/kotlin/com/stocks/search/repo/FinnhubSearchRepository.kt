package com.stocks.search.repo

import com.stocks.search.SearchResultItem
import com.stocks.search.repo.api.FinnhubSearchResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class FinnhubSearchRepository(
    private val client: HttpClient,
) {
     suspend fun search(query: String): List<SearchResultItem> {
        val response = client.get( "search") {
            parameter("q", query)
        }.body<FinnhubSearchResponse>()

        return response.result.map { item ->
            SearchResultItem(
                symbol = item.symbol,
                description = item.description,
                displaySymbol = item.displaySymbol,
                type = item.type,
            )
        }
    }
}
