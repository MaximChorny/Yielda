package com.stocks.search

import androidx.compose.ui.window.ComposeUIViewController
import org.koin.mp.KoinPlatform

fun MainViewController() = ComposeUIViewController {
    SearchRoute(viewModel = KoinPlatform.getKoin().get())
}
