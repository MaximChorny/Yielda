package com.stocks.yielda.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class YieldaColorScheme(
    internal val material: ColorScheme,
    val backgroundScreen: Color,
    val textSecondary: Color,
    val searchFieldBackground: Color,
) {
    val onBackground: Color get() = material.onBackground
    val primaryContainer: Color get() = material.primaryContainer
    val onPrimaryContainer: Color get() = material.onPrimaryContainer
    val error: Color get() = material.error
}

private val LocalYieldaTypography = staticCompositionLocalOf<YieldaTypography> {
    error("YieldaTypography is not provided")
}

private val LocalYieldaColorScheme = staticCompositionLocalOf<YieldaColorScheme> {
    error("YieldaColorScheme is not provided")
}

private val LocalYieldaIsDarkTheme = staticCompositionLocalOf { false }

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    background = BackgroundDark,
    surface = BackgroundDark,
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    background = BackgroundLight,
    surface = BackgroundLight,
)

object YieldaTheme {
    val colorScheme: YieldaColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalYieldaColorScheme.current

    val typography: YieldaTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalYieldaTypography.current

    val isDarkTheme: Boolean
        @Composable
        @ReadOnlyComposable
        get() = LocalYieldaIsDarkTheme.current

    @Composable
    operator fun invoke(
        darkTheme: Boolean = isSystemInDarkTheme(),
        dynamicColor: Boolean = true,
        content: @Composable () -> Unit,
    ) {
        val materialColorScheme = platformColorScheme(
            darkTheme = darkTheme,
            dynamicColor = dynamicColor,
        ) ?: if (darkTheme) DarkColorScheme else LightColorScheme

        val typography = yieldaTypography()
        val colorScheme = YieldaColorScheme(
            material = materialColorScheme,
            backgroundScreen = if (darkTheme) BackgroundDark else BackgroundLight,
            textSecondary = TextSecondary,
            searchFieldBackground = if (darkTheme) SearchFieldBackgroundDark else SearchFieldBackgroundLight,
        )

        CompositionLocalProvider(
            LocalYieldaColorScheme provides colorScheme,
            LocalYieldaTypography provides typography,
            LocalYieldaIsDarkTheme provides darkTheme,
        ) {
            MaterialTheme(
                colorScheme = materialColorScheme,
                typography = typography.toMaterialTypography(),
                content = content,
            )
        }
    }
}

@Composable
internal expect fun platformColorScheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
): ColorScheme?
