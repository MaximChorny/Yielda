package com.stocks.yielda.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.stocks.yielda.ui.generated.resources.Res
import com.stocks.yielda.ui.generated.resources.montserrat_bold
import com.stocks.yielda.ui.generated.resources.montserrat_medium
import com.stocks.yielda.ui.generated.resources.montserrat_regular
import com.stocks.yielda.ui.generated.resources.montserrat_semibold
import org.jetbrains.compose.resources.Font

@Immutable
data class YieldaTypography(
    val regular: TextStyle,
    val medium: TextStyle,
    val semiBold: TextStyle,
    val bold: TextStyle,
    val label: TextStyle,
    val toolbarTitle: TextStyle,
)

@Composable
fun montserratFontFamily() = FontFamily(
    Font(Res.font.montserrat_regular, FontWeight.Normal),
    Font(Res.font.montserrat_medium, FontWeight.Medium),
    Font(Res.font.montserrat_semibold, FontWeight.SemiBold),
    Font(Res.font.montserrat_bold, FontWeight.Bold),
)

@Composable
internal fun yieldaTypography(): YieldaTypography {
    val fontFamily = montserratFontFamily()

    return YieldaTypography(
        regular = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.sp,
        ),
        medium = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.sp,
        ),
        semiBold = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.sp,
        ),
        bold = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.sp,
        ),
        label = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.sp,
        ),
        toolbarTitle = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 24.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.sp,
        ),
    )
}

internal fun YieldaTypography.toMaterialTypography() = Typography(
    bodyLarge = regular,
    bodyMedium = regular.copy(
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),
    bodySmall = regular.copy(
        fontSize = 12.sp,
        lineHeight = 16.sp,
    ),
    titleLarge = toolbarTitle,
    titleMedium = semiBold.copy(
        fontSize = 16.sp,
        lineHeight = 24.sp,
    ),
    labelLarge = medium.copy(
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),
)
