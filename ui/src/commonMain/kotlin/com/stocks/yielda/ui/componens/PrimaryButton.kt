package com.stocks.yielda.ui.componens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stocks.yielda.ui.theme.YieldaTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun PrimaryButton(
    text: String,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(10.dp)
    val borderColor = Color(0xFFA6A6A6)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .then(
                if (enabled) {
                    Modifier.background(Color(0xFF1261F8), shape)
                } else {
                    Modifier.border(
                        width = 1.dp,
                        color = borderColor,
                        shape = shape,
                    )
                }
            )
            .clickable(
                enabled = enabled,
                onClick = onClick,
            )
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            color = if (enabled) Color.White else borderColor,
            textAlign = TextAlign.Center,
            style = YieldaTheme.typography.medium.copy(fontSize = 18.sp),
        )
    }
}

@Preview
@Composable
private fun PrimaryButtonPreview() {
    YieldaTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            PrimaryButton(
                text = "Buy",
                enabled = true,
                onClick = {},
            )
            PrimaryButton(
                text = "Buy",
                enabled = false,
                onClick = {},
            )
        }
    }
}
