package com.stocks.search

import androidx.compose.ui.window.ComposeUIViewController

fun MainViewController() = ComposeUIViewController {
    SearchScreen(SearchViewModel())
}
