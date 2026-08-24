package com.stocks.yielda.ui.componens.stockchart

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stocks.yielda.ui.theme.YieldaTheme
import kotlin.math.roundToInt

@Composable
fun StockPriceChart(
    points: List<PricePoint>,
    selectedPeriod: ChartPeriod,
    currencySymbol: String,
    onPeriodSelected: (ChartPeriod) -> Unit,
    modifier: Modifier = Modifier,
) {
    StockPriceChart(
        state = if (points.isEmpty()) StockPriceChartState.Empty else StockPriceChartState.Content(
            points
        ),
        selectedPeriod = selectedPeriod,
        currencySymbol = currencySymbol,
        onPeriodSelected = onPeriodSelected,
        modifier = modifier,
    )
}

@Composable
fun StockPriceChart(
    state: StockPriceChartState,
    selectedPeriod: ChartPeriod,
    currencySymbol: String,
    onPeriodSelected: (ChartPeriod) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(YieldaTheme.colorScheme.backgroundScreen)
            .padding(horizontal = 16.dp, vertical = 14.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
            contentAlignment = Alignment.Center,
        ) {
            when (state) {
                StockPriceChartState.Loading -> ChartLoading()
                StockPriceChartState.Empty -> ChartMessage("No chart data")
                is StockPriceChartState.Error -> ChartMessage(state.message.ifBlank { "Unable to load chart" })
                is StockPriceChartState.Content -> {
                    if (state.points.isEmpty()) {
                        ChartMessage("No chart data")
                    } else {
                        Crossfade(
                            targetState = state.points,
                            animationSpec = tween(durationMillis = 240),
                            label = "stock-chart-points",
                        ) { animatedPoints ->
                            ChartContent(
                                points = animatedPoints,
                                currencySymbol = currencySymbol,
                                modifier = Modifier.fillMaxSize(),
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        PeriodSelector(
            selectedPeriod = selectedPeriod,
            onPeriodSelected = onPeriodSelected,
        )
    }
}

@Composable
private fun ChartContent(
    points: List<PricePoint>,
    currencySymbol: String,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .padding(top = 6.dp, bottom = 6.dp),
        ) {
            InteractiveChartCanvas(
                points = points,
                currencySymbol = currencySymbol,
                modifier = Modifier.fillMaxSize(),
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        PriceScale(
            points = points,
            currencySymbol = currencySymbol,
            modifier = Modifier
                .widthIn(min = 62.dp)
                .heightIn(min = 180.dp)
                .padding(vertical = 6.dp),
        )
    }
}

@Composable
private fun BoxScope.InteractiveChartCanvas(
    points: List<PricePoint>,
    currencySymbol: String,
    modifier: Modifier = Modifier,
) {
    val hapticFeedback = LocalHapticFeedback.current
    var canvasSize by remember { mutableStateOf(IntSize.Zero) }
    var selectedIndex by remember(points) { mutableStateOf<Int?>(null) }
    var interactionVisible by remember(points) { mutableStateOf(false) }
    var lastHapticIndex by remember(points) { mutableIntStateOf(-1) }
    val interactionAlpha by animateFloatAsState(
        targetValue = if (interactionVisible && selectedIndex != null) 1f else 0f,
        animationSpec = tween(durationMillis = 180),
        label = "chart-marker-alpha",
    )

    LaunchedEffect(points) {
        selectedIndex = null
        interactionVisible = false
        lastHapticIndex = -1
    }

    val geometry = remember(points, canvasSize) {
        buildChartGeometry(
            points = points,
            width = canvasSize.width.toFloat(),
            height = canvasSize.height.toFloat(),
        )
    }

    Canvas(
        modifier = modifier
            .onSizeChanged { canvasSize = it }
            .pointerInput(points, canvasSize) {
                detectDragGesturesAfterLongPress(
                    onDragStart = { offset ->
                        val pointXs = geometry?.points.orEmpty().map { it.x }
                        val index = nearestPricePointIndex(offset.x, pointXs)
                        selectedIndex = index
                        interactionVisible = index != null
                        if (index != null) {
                            hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                            lastHapticIndex = index
                        }
                    },
                    onDragEnd = {
                        interactionVisible = false
                        lastHapticIndex = -1
                    },
                    onDragCancel = {
                        interactionVisible = false
                        lastHapticIndex = -1
                    },
                    onDrag = { change, _ ->
                        val pointXs = geometry?.points.orEmpty().map { it.x }
                        val index = nearestPricePointIndex(change.position.x, pointXs)
                        selectedIndex = index
                        if (index != null && index != lastHapticIndex) {
                            hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                            lastHapticIndex = index
                        }
                    },
                )
            },
    ) {
        val currentGeometry = geometry ?: return@Canvas
        val linePath = currentGeometry.points.toMonotonePath()
        val fillPath = currentGeometry.points.toFilledMonotonePath(size.height)

        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colorStops = arrayOf(
                    0f to ChartBlue.copy(alpha = 0.50f),
                    0.45f to ChartBlue.copy(alpha = 0.25f),
                    0.80f to ChartBlue.copy(alpha = 0.08f),
                    1f to ChartBlue.copy(alpha = 0f),
                ),
                startY = 0f,
                endY = size.height,
            ),
        )

        drawPath(
            path = linePath,
            color = ChartBlue,
            style = Stroke(width = 2.5.dp.toPx(), cap = StrokeCap.Round),
        )

        val selectedPoint = selectedIndex?.let { currentGeometry.points.getOrNull(it) }
        if (selectedPoint != null && interactionAlpha > 0f) {
            drawLine(
                color = ChartBlue.copy(alpha = 0.35f * interactionAlpha),
                start = Offset(selectedPoint.x, selectedPoint.y),
                end = Offset(selectedPoint.x, size.height),
                strokeWidth = 1.dp.toPx(),
            )
            drawCircle(
                color = Color.White.copy(alpha = interactionAlpha),
                radius = 6.dp.toPx(),
                center = Offset(selectedPoint.x, selectedPoint.y),
            )
            drawCircle(
                color = ChartBlue.copy(alpha = interactionAlpha),
                radius = 4.dp.toPx(),
                center = Offset(selectedPoint.x, selectedPoint.y),
            )
        }
    }

    val selectedPoint = selectedIndex?.let { geometry?.points?.getOrNull(it) }
    if (selectedPoint != null && interactionAlpha > 0f) {
        PriceTooltip(
            point = selectedPoint,
            canvasSize = canvasSize,
            currencySymbol = currencySymbol,
            modifier = Modifier.alpha(interactionAlpha),
        )
    }
}

@Composable
private fun BoxScope.PriceTooltip(
    point: ChartPoint,
    canvasSize: IntSize,
    currencySymbol: String,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    val tooltipWidth = 79.dp
    val tooltipHeight = 24.dp
    val gap = 8.dp
    val tooltipWidthPx = with(density) { tooltipWidth.toPx() }
    val tooltipHeightPx = with(density) { tooltipHeight.toPx() }
    val gapPx = with(density) { gap.toPx() }

    val placeOnRight = point.x + gapPx + tooltipWidthPx <= canvasSize.width
    val x = if (placeOnRight) {
        point.x + gapPx
    } else {
        point.x - tooltipWidthPx - gapPx
    }.coerceIn(0f, (canvasSize.width - tooltipWidthPx).coerceAtLeast(0f))
    val y = (point.y - tooltipHeightPx / 2f).coerceIn(
        0f,
        (canvasSize.height - tooltipHeightPx).coerceAtLeast(0f)
    )

    Box(
        modifier = modifier
            .offset { IntOffset(x.roundToInt(), y.roundToInt()) }
            .requiredSize(width = tooltipWidth, height = tooltipHeight)
            .background(ChartBlue, RoundedCornerShape(20.dp))
            .padding(horizontal = 10.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = formatChartCurrency(point.source.price, currencySymbol),
            color = Color.White,
            style = YieldaTheme.typography.regular.copy(fontSize = 12.sp,),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
private fun PriceScale(
    points: List<PricePoint>,
    currencySymbol: String,
    modifier: Modifier = Modifier,
) {
    val range = remember(points) { priceRange(points) }
    val animatedMax by animateFloatAsState(
        targetValue = (range?.maxPrice ?: 0.0).toFloat(),
        animationSpec = tween(durationMillis = 240),
        label = "chart-scale-max",
    )
    val animatedMid by animateFloatAsState(
        targetValue = (range?.midPrice ?: 0.0).toFloat(),
        animationSpec = tween(durationMillis = 240),
        label = "chart-scale-mid",
    )
    val animatedMin by animateFloatAsState(
        targetValue = (range?.minPrice ?: 0.0).toFloat(),
        animationSpec = tween(durationMillis = 240),
        label = "chart-scale-min",
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.End,
    ) {
        PriceScaleLabel(formatChartCurrency(animatedMax.toDouble(), currencySymbol))
        PriceScaleLabel(formatChartCurrency(animatedMid.toDouble(), currencySymbol))
        PriceScaleLabel(formatChartCurrency(animatedMin.toDouble(), currencySymbol))
    }
}

@Composable
private fun PriceScaleLabel(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        modifier = modifier.widthIn(max = 54.dp),
        color = YieldaTheme.colorScheme.textSecondary,
        style = YieldaTheme.typography.regular.copy(fontSize = 12.sp),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        textAlign = TextAlign.End,
    )
}

@Composable
private fun PeriodSelector(
    selectedPeriod: ChartPeriod,
    onPeriodSelected: (ChartPeriod) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .selectableGroup(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ChartPeriod.entries.forEach { period ->
            PeriodButton(
                period = period,
                selected = period == selectedPeriod,
                onClick = { onPeriodSelected(period) },
            )
        }
    }
}

@Composable
private fun PeriodButton(
    period: ChartPeriod,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .height(44.dp)
            .widthIn(min = 44.dp)
            .padding(horizontal = 2.dp),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(
                    color = if (selected) ActivePeriodBackground else Color.Transparent,
                    shape = RoundedCornerShape(20.dp),
                )
                .selectable(
                    selected = selected,
                    onClick = onClick,
                )
                .padding(horizontal = 14.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = period.name,
                color = if (selected) ActivePeriodText else InactivePeriodText,
                style = YieldaTheme.typography.regular.copy(fontSize = 12.sp),
                maxLines = 1,
            )
        }
    }
}

@Composable
private fun ChartLoading() {
    CircularProgressIndicator(
        modifier = Modifier.size(28.dp),
        color = ChartBlue,
        strokeWidth = 2.dp,
    )
}

@Composable
private fun ChartMessage(text: String) {
    Text(
        text = text,
        color = ScaleText,
        style = YieldaTheme.typography.medium.copy(fontSize = 13.sp),
        textAlign = TextAlign.Center,
    )
}

private fun List<ChartPoint>.toMonotonePath(): Path {
    val path = Path()
    if (isEmpty()) return path

    path.moveTo(first().x, first().y)
    if (size == 1) {
        path.lineTo(first().x, first().y)
        return path
    }

    monotoneCubicControlPoints(this).forEach { control ->
        path.cubicTo(
            x1 = control.firstX,
            y1 = control.firstY,
            x2 = control.secondX,
            y2 = control.secondY,
            x3 = control.endX,
            y3 = control.endY,
        )
    }

    return path
}

private fun List<ChartPoint>.toFilledMonotonePath(height: Float): Path {
    val path = toMonotonePath()
    if (isEmpty()) return path

    path.lineTo(last().x, height)
    path.lineTo(first().x, height)
    path.close()

    return path
}

@Composable
fun StockPriceChartDemo(modifier: Modifier = Modifier) {
    var selectedPeriod by remember { mutableStateOf(ChartPeriod.Month) }
    val points = remember(selectedPeriod) { demoPoints(selectedPeriod) }

    YieldaTheme(
        darkTheme = false,
        dynamicColor = false,
    ) {
        StockPriceChart(
            points = points,
            selectedPeriod = selectedPeriod,
            currencySymbol = "$",
            onPeriodSelected = { selectedPeriod = it },
            modifier = modifier,
        )
    }
}

private fun demoPoints(period: ChartPeriod): List<PricePoint> {
    val count = when (period) {
        ChartPeriod.Day -> 18
        ChartPeriod.Week -> 28
        ChartPeriod.Month -> 42
        ChartPeriod.Year -> 64
        ChartPeriod.All -> 96
    }
    val start = 1_700_000_000_000L
    val step = 86_400_000L

    return List(count) { index ->
        val trend = index * 1.75
        val wave = ((index % 9) - 4) * 4.8
        val pullback = if (index % 17 > 12) -18.0 else 0.0
        PricePoint(
            timestamp = start + index * step,
            price = 280.0 + trend + wave + pullback,
        )
    }
}

private val ChartBlue = Color(0xFF1261F8)
private val ScaleText = Color(0xFFB5B8C0)
private val ActivePeriodBackground = Color.White
private val ActivePeriodText = Color.Black
private val InactivePeriodText = Color(0xFF4D4D4D)
