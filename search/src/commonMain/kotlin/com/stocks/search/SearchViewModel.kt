package com.stocks.search

import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.ViewModel

class SearchViewModel(
    private val searchStore: SearchStore = SearchStore(),
) : ViewModel() {
    val state: StateFlow<SearchUiState> = searchStore.state

    fun onQueryChange(query: String) {
        searchStore.onQueryChange(query)
    }
}
