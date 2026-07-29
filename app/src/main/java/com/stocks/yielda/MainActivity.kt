package com.stocks.yielda

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.stocks.search.SearchViewModel
import com.stocks.search.SearchRoute
import com.stocks.yielda.ui.theme.YieldaTheme
import org.koin.compose.koinInject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YieldaTheme {
                SearchRoute(viewModel = koinInject<SearchViewModel>())
            }
        }
    }
}
