package com.stocks.yielda.ui.componens.stockchart

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stocks.yielda.ui.theme.YieldaTheme

@Preview(
    name = "Stock Price Chart",
    showBackground = true,
    backgroundColor = 0xFFF9F9F9,
    widthDp = 390,
    heightDp = 320,
)
@Composable
private fun StockPriceChartPreview() {
    StockPriceChartDemo(
        modifier = Modifier.padding(16.dp),
    )
}

@Preview(
    name = "Stock Price Chart Loading",
    showBackground = true,
    backgroundColor = 0xFFF9F9F9,
    widthDp = 390,
    heightDp = 320,
)
@Composable
private fun StockPriceChartLoadingPreview() {
    StockPriceChartPreviewHost(
        state = StockPriceChartState.Loading,
    )
}

@Preview(
    name = "Stock Price Chart Empty",
    showBackground = true,
    backgroundColor = 0xFFF9F9F9,
    widthDp = 390,
    heightDp = 320,
)
@Composable
private fun StockPriceChartEmptyPreview() {
    StockPriceChartPreviewHost(
        state = StockPriceChartState.Empty,
    )
}

@Composable
private fun StockPriceChartPreviewHost(
    state: StockPriceChartState,
) {
    YieldaTheme(
        darkTheme = false,
        dynamicColor = false,
    ) {
        StockPriceChart(
            state = state,
            selectedPeriod = ChartPeriod.Month,
            currencySymbol = "$",
            onPeriodSelected = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}
