package com.stocks.search

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun SearchScreen(state: SearchUiState) {
    Text(text = "Search: ${state.query}")
}
