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

internal val LocalColorTheme = compositionLocalOf<ColorTheme> { error("No ColorTheme provided") }
internal val LocalTypography = compositionLocalOf<GomsTypography> { error("No GomsTypography provided") }

@Composable
fun GomsTheme(
    themeMode: String = ThemeType.Dark.name,
    content: @Composable (colors: ColorTheme, typography: GomsTypography) -> Unit
) {
    val theme = when (themeMode) {
        ThemeType.Dark.name -> true
        ThemeType.Light.name -> false
        ThemeType.System.name -> isSystemInDarkTheme()
        else -> true
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

