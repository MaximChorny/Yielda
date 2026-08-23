package com.stocks.search

import androidx.compose.ui.window.ComposeUIViewController
import com.stocks.yielda.ui.theme.YieldaTheme
import org.koin.mp.KoinPlatform

fun MainViewController() = ComposeUIViewController {
    YieldaTheme {
        SearchRoute(viewModel = KoinPlatform.getKoin().get())
    }
}
