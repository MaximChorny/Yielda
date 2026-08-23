package com.stocks.search.repo

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.stocks.search.SearchResultItem
import com.stocks.search.db.SearchDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow

class RecentSearchRepository(
    private val database: SearchDatabase,
) {
    fun observeRecentSearches(): Flow<List<SearchResultItem>> {
        return recentSearchesQuery()
            .asFlow()
            .mapToList(Dispatchers.IO)
    }

    private fun recentSearchesQuery() = database.recentSearchQueries.selectRecentSearches(
        mapper = { symbol, description, displaySymbol, type, iconUrl ->
            SearchResultItem(
                symbol = symbol,
                description = description,
                displaySymbol = displaySymbol,
                type = type,
                iconUrl = iconUrl,
            )
        },
    )

    fun saveRecentSearch(item: SearchResultItem) {
        database.transaction {
            database.recentSearchQueries.deleteRecentSearch(symbol = item.symbol)
            database.recentSearchQueries.saveRecentSearch(
                symbol = item.symbol,
                description = item.description,
                display_symbol = item.displaySymbol,
                type = item.type,
                icon_url = item.iconUrl,
            )
            database.recentSearchQueries.trimRecentSearches()
        }
    }
}
