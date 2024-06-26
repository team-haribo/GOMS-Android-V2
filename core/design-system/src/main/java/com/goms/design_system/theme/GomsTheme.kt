package com.goms.design_system.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import com.goms.design_system.theme.color.DarkColor
import com.goms.design_system.theme.color.LightColor
import com.goms.design_system.util.ApplySystemUi

enum class ThemeType(val value: String, val kr: String) {
    DARK("Dark", "다크(기본)"),
    LIGHT("Light", "라이트"),
    SYSTEM("System", "시스템 테마 설정")
}

internal val LocalColorTheme = compositionLocalOf<ColorTheme> { error("No ColorTheme provided") }
internal val LocalTypography = compositionLocalOf<GomsTypography> { error("No GomsTypography provided") }

@Composable
fun GomsTheme(
    themeMode: String = ThemeType.DARK.value,
    content: @Composable () -> Unit
) {
    val theme = when (themeMode) {
        ThemeType.DARK.value -> true
        ThemeType.LIGHT.value -> false
        ThemeType.SYSTEM.value -> isSystemInDarkTheme()
        else -> true
    }

    val colors = if (theme) DarkColor else LightColor
    val typography = GomsTypography

    CompositionLocalProvider(
        LocalColorTheme provides colors,
        LocalTypography provides typography
    ) {
        ApplySystemUi(theme)
        content()
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

