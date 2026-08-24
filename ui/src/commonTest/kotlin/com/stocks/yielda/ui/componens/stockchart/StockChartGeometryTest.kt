package com.stocks.yielda.ui.componens.stockchart

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class StockChartGeometryTest {
    @Test
    fun nearestPricePointIndexReturnsClosestHorizontalPoint() {
        val index = nearestPricePointIndex(
            touchX = 72f,
            pointXs = listOf(0f, 40f, 80f, 120f),
        )

        assertEquals(2, index)
    }

    @Test
    fun nearestPricePointIndexReturnsNullWhenThereAreNoPoints() {
        assertEquals(null, nearestPricePointIndex(24f, emptyList()))
    }

    @Test
    fun buildChartGeometryHandlesEqualPrices() {
        val geometry = buildChartGeometry(
            points = listOf(
                PricePoint(timestamp = 1L, price = 100.0),
                PricePoint(timestamp = 2L, price = 100.0),
                PricePoint(timestamp = 3L, price = 100.0),
            ),
            width = 200f,
            height = 120f,
        )

        assertNotNull(geometry)
        assertEquals(3, geometry.points.size)
        assertEquals(92.0, geometry.range.paddedMin)
        assertEquals(108.0, geometry.range.paddedMax)
        assertEquals(60f, geometry.points.first().y)
    }
}
