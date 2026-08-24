package com.stocks.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stocks.search.repo.FinnhubSearchRepository
import com.stocks.search.repo.RecentSearchRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

class SearchViewModel(
    private val repository: FinnhubSearchRepository,
    private val recentSearchRepository: RecentSearchRepository,
) : ViewModel() {

    val results = MutableStateFlow<List<SearchResultItem>>(emptyList())
    val recentSearchResults: StateFlow<List<SearchResultItem>> = recentSearchRepository
        .observeRecentSearches()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList(),
        )

    val query = MutableStateFlow("")
    val isLoading = MutableStateFlow(false)
    private var saveSearchResultsJob: Job? = null

    fun start() {
        if (saveSearchResultsJob?.isActive == true) {
            return
        }

        viewModelScope.launch {
            query.collectLatest { currentQuery ->
                val trimmedQuery = currentQuery.trim()
                if (trimmedQuery.isEmpty()) {
                    results.value = emptyList()
                    isLoading.value = false
                    return@collectLatest
                }

                results.value = emptyList()
                isLoading.value = true

                try {
                    delay(300.milliseconds)
                    val searchResults = repository.search(trimmedQuery)
                    results.value = searchResults
                } catch (throwable: Throwable) {
                    if (throwable is CancellationException) {
                        throw throwable
                    }

                    results.value = emptyList()
                } finally {
                    if (currentCoroutineContext().isActive) {
                        isLoading.value = false
                    }
                }
            }
        }

        saveSearchResultsJob = viewModelScope.launch {
            combine(query, results) { currentQuery, currentResults ->
                currentQuery.trim() to currentResults
            }
                .filter { (currentQuery, currentResults) ->
                    currentQuery.isNotEmpty() && currentResults.isNotEmpty()
                }
                .collectLatest { (_, currentResults) ->
                    currentResults.take(3).forEach { resultItem ->
                        val iconUrl = runCatching {
                            repository.getCompanyProfile(resultItem.symbol).logo
                        }.getOrDefault(resultItem.iconUrl)
                        saveResultItem(resultItem.copy(iconUrl = iconUrl))
                        delay(1.seconds)
                    }
                }
        }
    }

    fun onQueryChange(newQuery: String) {
        query.value = newQuery
    }

    private suspend fun saveResultItem(item: SearchResultItem) {
        withContext(Dispatchers.IO) {
            recentSearchRepository.saveRecentSearch(item)
        }
    }
}
