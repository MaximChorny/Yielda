package com.stocks.yielda.ui.componens.stockchart

import kotlin.math.abs
import kotlin.math.max

data class ChartPoint(
    val source: PricePoint,
    val x: Float,
    val y: Float,
)

data class PriceRange(
    val minPrice: Double,
    val maxPrice: Double,
    val paddedMin: Double,
    val paddedMax: Double,
) {
    val midPrice: Double get() = (minPrice + maxPrice) / 2.0
}

data class ChartGeometry(
    val points: List<ChartPoint>,
    val range: PriceRange,
    val width: Float,
    val height: Float,
)

fun nearestPricePointIndex(touchX: Float, pointXs: List<Float>): Int? {
    if (pointXs.isEmpty()) return null

    var nearestIndex = 0
    var nearestDistance = abs(pointXs.first() - touchX)

    for (index in 1..pointXs.lastIndex) {
        val distance = abs(pointXs[index] - touchX)
        if (distance < nearestDistance) {
            nearestDistance = distance
            nearestIndex = index
        }
    }

    return nearestIndex
}

fun priceRange(points: List<PricePoint>, paddingFraction: Double = 0.08): PriceRange? {
    if (points.isEmpty()) return null

    val minPrice = points.minOf { it.price }
    val maxPrice = points.maxOf { it.price }
    val rawRange = maxPrice - minPrice
    val padding = when {
        rawRange > 0.0 -> rawRange * paddingFraction
        maxPrice != 0.0 -> abs(maxPrice) * paddingFraction
        else -> 1.0
    }

    return PriceRange(
        minPrice = minPrice,
        maxPrice = maxPrice,
        paddedMin = minPrice - padding,
        paddedMax = maxPrice + padding,
    )
}

fun buildChartGeometry(
    points: List<PricePoint>,
    width: Float,
    height: Float,
): ChartGeometry? {
    if (points.isEmpty() || width <= 0f || height <= 0f) return null

    val sortedPoints = points.sortedBy { it.timestamp }
    val range = priceRange(sortedPoints) ?: return null
    val minTimestamp = sortedPoints.first().timestamp
    val maxTimestamp = sortedPoints.last().timestamp
    val timestampRange = max(1L, maxTimestamp - minTimestamp).toDouble()
    val priceRange = max(0.000001, range.paddedMax - range.paddedMin)

    val chartPoints = sortedPoints.map { point ->
        val x = if (sortedPoints.size == 1) {
            width / 2f
        } else {
            (((point.timestamp - minTimestamp) / timestampRange) * width).toFloat()
        }
        val normalizedPrice = ((point.price - range.paddedMin) / priceRange).coerceIn(0.0, 1.0)
        ChartPoint(
            source = point,
            x = x.coerceIn(0f, width),
            y = (height - (normalizedPrice * height)).toFloat().coerceIn(0f, height),
        )
    }

    return ChartGeometry(
        points = chartPoints,
        range = range,
        width = width,
        height = height,
    )
}

data class MonotoneControlPoints(
    val firstX: Float,
    val firstY: Float,
    val secondX: Float,
    val secondY: Float,
    val endX: Float,
    val endY: Float,
)

fun monotoneCubicControlPoints(points: List<ChartPoint>): List<MonotoneControlPoints> {
    if (points.size < 2) return emptyList()

    val slopes = FloatArray(points.lastIndex)
    for (index in 0 until points.lastIndex) {
        val dx = points[index + 1].x - points[index].x
        slopes[index] = if (dx == 0f) 0f else (points[index + 1].y - points[index].y) / dx
    }

    val tangents = FloatArray(points.size)
    tangents[0] = slopes[0]
    tangents[points.lastIndex] = slopes[slopes.lastIndex]

    for (index in 1 until points.lastIndex) {
        val previousSlope = slopes[index - 1]
        val nextSlope = slopes[index]
        tangents[index] = if (previousSlope * nextSlope <= 0f) {
            0f
        } else {
            (previousSlope + nextSlope) / 2f
        }
    }

    for (index in slopes.indices) {
        val slope = slopes[index]
        if (slope == 0f) {
            tangents[index] = 0f
            tangents[index + 1] = 0f
        } else {
            val first = tangents[index] / slope
            val second = tangents[index + 1] / slope
            val sum = first * first + second * second
            if (sum > 9f) {
                val scale = 3f / kotlin.math.sqrt(sum)
                tangents[index] = scale * first * slope
                tangents[index + 1] = scale * second * slope
            }
        }
    }

    return (0 until points.lastIndex).map { index ->
        val start = points[index]
        val end = points[index + 1]
        val dx = end.x - start.x
        MonotoneControlPoints(
            firstX = start.x + dx / 3f,
            firstY = start.y + tangents[index] * dx / 3f,
            secondX = end.x - dx / 3f,
            secondY = end.y - tangents[index + 1] * dx / 3f,
            endX = end.x,
            endY = end.y,
        )
    }
}

internal fun Double.trimmedDecimal(maxFractionDigits: Int = 2): String {
    val rounded = roundToFractionDigits(maxFractionDigits)
    val raw = rounded.toString()
    return if (raw.contains('.')) raw.trimEnd('0').trimEnd('.') else raw
}

internal fun Double.roundToFractionDigits(fractionDigits: Int): Double {
    var multiplier = 1.0
    repeat(fractionDigits) {
        multiplier *= 10.0
    }
    return kotlin.math.round(this * multiplier) / multiplier
}

internal expect fun formatChartCurrency(
    value: Double,
    currencySymbol: String,
): String
