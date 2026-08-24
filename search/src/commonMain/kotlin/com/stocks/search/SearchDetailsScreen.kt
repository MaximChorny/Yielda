package com.stocks.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stocks.yielda.ui.componens.MainToolbar
import com.stocks.yielda.ui.componens.PrimaryButton
import com.stocks.yielda.ui.componens.stockchart.PricePoint
import com.stocks.yielda.ui.componens.stockchart.StockPriceChart
import com.stocks.yielda.ui.componens.stockchart.StockPriceChartState
import com.stocks.yielda.ui.theme.YieldaTheme

@Composable
fun SearchDetailsScreen(
    viewModel: SearchViewModel,
    onBackClick: () -> Unit,
) {
    val selectedItem = viewModel.selectedSearchResult.collectAsStateWithLifecycle().value ?: return
    val selectedStockQuote = viewModel.selectedStockQuote.collectAsStateWithLifecycle().value
    val selectedStockChartPeriod =
        viewModel.selectedStockChartPeriod.collectAsStateWithLifecycle().value
    val selectedStockChartPoints =
        viewModel.selectedStockChartPoints.collectAsStateWithLifecycle().value
    val isSelectedStockChartLoading =
        viewModel.isSelectedStockChartLoading.collectAsStateWithLifecycle().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(YieldaTheme.colorScheme.backgroundScreen)
            .padding(horizontal = 16.dp)
            .statusBarsPadding()
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        MainToolbar(
            title = "Search",
            iconUrl = "https://picsum.photos/200/300",
            iconText = "Maksym",
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = selectedItem.description,
            color = YieldaTheme.colorScheme.onBackground,
            style = YieldaTheme.typography.medium.copy(fontSize = 24.sp),
        )
        StockPriceRow(
            quote = selectedStockQuote,
        )
        StockPriceChart(
            state = if (isSelectedStockChartLoading) {
                StockPriceChartState.Loading
            } else {
                StockPriceChartState.Content(selectedStockChartPoints.toPricePoints())
            },
            selectedPeriod = selectedStockChartPeriod,
            currencySymbol = "$",
            onPeriodSelected = { period ->
                viewModel.onSelectedStockChartPeriodChange(period)
            },
        )

        Spacer(Modifier.weight(1f))

        PrimaryButton(enabled = true, text = "Buy", onClick = {

        })
    }
}

@Composable
private fun StockPriceRow(
    quote: StockQuote?,
    modifier: Modifier = Modifier,
) {
    val change = quote?.change ?: 0.0
    val changeColor = when {
        change > 0.0 -> Color(0xFF1261F8)
        change < 0.0 -> Color.Red
        else -> YieldaTheme.colorScheme.textSecondary
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = YieldaTheme.colorScheme.searchFieldBackground,
                shape = RoundedCornerShape(15.dp),
            )
            .padding(20.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Bottom,
    ) {
        Text(
            text = when {
                quote != null -> "${quote.currentPrice}\$"
                else -> "N/A"
            },
            color = YieldaTheme.colorScheme.onBackground,
            style = YieldaTheme.typography.medium.copy(fontSize = 36.sp),
        )

        Spacer(modifier = Modifier.width(20.dp))

        Text(
            text = when {
                quote != null -> "${change.withSign()}\$ (${quote.percentChange.withSign()}%)"
                else -> ""
            },
            color = changeColor,
            style = YieldaTheme.typography.regular.copy(fontSize = 16.sp),
        )
    }
}

private fun Double.withSign(): String =
    if (this > 0.0) {
        "+$this"
    } else {
        toString()
    }

private fun List<StockPricePoint>.toPricePoints(): List<PricePoint> =
    map { point ->
        PricePoint(
            timestamp = point.timestamp,
            price = point.price,
        )
    }
