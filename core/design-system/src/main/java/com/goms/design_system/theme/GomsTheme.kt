package com.goms.design_system.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import com.goms.design_system.theme.color.DarkColor
import com.goms.design_system.theme.color.LightColor
import com.goms.design_system.util.ApplySystemUi

enum class ThemeType(val value: String) {
    Dark("Dark"),
    Light("Light"),
    System("System")
}

val LocalColorTheme = compositionLocalOf<ColorTheme> { error("No ColorTheme provided") }
val LocalTypography = compositionLocalOf<GomsTypography> { error("No GomsTypography provided") }

@Composable
fun GomsTheme(
    themeMode: ThemeType = ThemeType.System,
    content: @Composable (colors: ColorTheme, typography: GomsTypography) -> Unit
) {
    val theme = when (themeMode) {
        ThemeType.Dark -> true
        ThemeType.Light -> false
        ThemeType.System -> isSystemInDarkTheme()
    }

    val colors = if (theme) DarkColor else LightColor
    val typography = GomsTypography

    CompositionLocalProvider(
        LocalColorTheme provides colors,
        LocalTypography provides typography
    ) {
        ApplySystemUi(theme)
        content(colors, typography)
    }
}

object GomsTheme {
    val colors: ColorTheme
        @Composable
        get() = LocalColorTheme.current
    val typography: GomsTypography
        @Composable
        get() = LocalTypography.current
}

