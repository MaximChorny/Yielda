package com.stocks.yielda.ui.componens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.stocks.yielda.ui.theme.YieldaTheme


@Composable
fun MainToolbar(
    title: String,
    iconUrl: String?,
    iconText: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            modifier = Modifier
                .padding(end = 12.dp),
            color = YieldaTheme.colorScheme.onBackground,
            style = YieldaTheme.typography.toolbarTitle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        if (iconUrl != null) {
            AsyncImage(
                model = iconUrl,
                contentDescription = iconText.ifBlank { title },
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
            )
        } else {
            ToolbarInitialsIcon(text = iconText.ifBlank { title })
        }
    }
}

@Composable
private fun ToolbarInitialsIcon(text: String) {
    Box(
        modifier = Modifier.size(50.dp)
            .background(iconBackgroundColor(text), CircleShape)
            .clip(CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text.firstLetter(),
            textAlign = TextAlign.Center,
            style = YieldaTheme.typography.medium.copy(fontSize = 24.sp),
            maxLines = 1,
        )
    }
}

private fun iconBackgroundColor(text: String): Color {
    val index = text.hashCode().mod(IconBackgroundColors.size)
    return IconBackgroundColors[index]
}

private fun String.firstLetter(): String = firstOrNull()?.uppercase() ?: ""

private val IconBackgroundColors = listOf(
    Color(0xFFE9D5FF),
    Color(0xFFBFDBFE),
    Color(0xFFBBF7D0),
    Color(0xFFFDE68A),
    Color(0xFFFECACA),
)
