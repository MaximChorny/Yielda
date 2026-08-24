package com.stocks.search.repo

import com.stocks.search.SearchResultItem
import com.stocks.search.db.SearchDatabase
import com.stocks.search.repo.api.FinnhubCompanyProfileDto
import com.stocks.search.repo.api.FinnhubSearchResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class FinnhubSearchRepository(
    private val client: HttpClient,
    private val database: SearchDatabase,
) {
    suspend fun search(query: String): List<SearchResultItem> {
        val response = client.get("search") {
            parameter("q", query)
            parameter("exchange", "US")
        }.body<FinnhubSearchResponse>()

        return response.result.map { item ->
            SearchResultItem(
                symbol = item.symbol,
                description = item.description,
                displaySymbol = item.displaySymbol,
                type = item.type,
                iconUrl = getCompanyProfileLogo(item.symbol),
            )
        }
    }

    suspend fun getCompanyProfile(symbol: String): FinnhubCompanyProfileDto {
        val profile = client.get("stock/profile2") {
            parameter("symbol", symbol)
        }.body<FinnhubCompanyProfileDto>()

        if (profile.ticker.isNotBlank()) {
            saveCompanyProfile(profile)
        }
        return profile
    }

    suspend fun saveCompanyProfile(profile: FinnhubCompanyProfileDto) {
        if (profile.ticker.isBlank()) {
            return
        }

        withContext(Dispatchers.IO) {
            database.companyProfileQueries.saveCompanyProfile(
                ticker = profile.ticker,
                name = profile.name,
                country = profile.country,
                currency = profile.currency,
                estimate_currency = profile.estimateCurrency,
                exchange = profile.exchange,
                ipo = profile.ipo,
                market_capitalization = profile.marketCapitalization,
                logo = profile.logo,
                share_outstanding = profile.shareOutstanding,
                finnhub_industry = profile.finnhubIndustry,
                phone = profile.phone,
                weburl = profile.weburl,
                floating_share = profile.floatingShare,
            )
        }
    }

    private suspend fun getCompanyProfileLogo(symbol: String): String {
        return withContext(Dispatchers.IO) {
            database.companyProfileQueries
                .selectCompanyProfileLogo(ticker = symbol)
                .executeAsOneOrNull()
                .orEmpty()
        }
    }
}
