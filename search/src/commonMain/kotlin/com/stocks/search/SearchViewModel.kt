package com.stocks.search

import androidx.lifecycle.ViewModel
import com.stocks.search.repo.FinnhubSearchRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: FinnhubSearchRepository,
) : ViewModel() {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private val _state = MutableStateFlow(SearchUiState())
    val state: StateFlow<SearchUiState> = _state.asStateFlow()
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()
    private var searchJob: Job? = null

    fun onQueryChange(query: String) {
        _query.value = query
        _state.value = _state.value.copy(errorMessage = null)

        searchJob?.cancel()

        val trimmedQuery = query.trim()
        if (trimmedQuery.isEmpty()) {
            _state.value = SearchUiState()
            return
        }

        searchJob = scope.launch {
            delay(300)
            if (_query.value.trim() != trimmedQuery) {
                return@launch
            }

            _state.value = _state.value.copy(isLoading = true, results = emptyList())
            try {
                val results = repository.search(trimmedQuery)
                _state.value = _state.value.copy(
                    isLoading = false,
                    results = results,
                    errorMessage = null,
                )
            } catch (throwable: Throwable) {
                if (throwable is CancellationException) {
                    throw throwable
                }

                _state.value = _state.value.copy(
                    isLoading = false,
                    results = emptyList(),
                    errorMessage = throwable.message ?: "Search failed",
                )
            }
        }
    }

    override fun onCleared() {
        searchJob?.cancel()
        scope.cancel()
    }
}
