package com.goms.design_system.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.goms.design_system.theme.color.DarkColor
import com.goms.design_system.theme.color.LightColor
import com.goms.design_system.util.ApplySystemUi

@Composable
fun GomsTheme(
    colors: ColorTheme = if (isSystemInDarkTheme()) DarkColor else LightColor,
    typography: GomsTypography = GomsTypography,
    content: @Composable (colors: ColorTheme, typography: GomsTypography) -> Unit
) {
    content(colors, typography)
    ApplySystemUi(isSystemInDarkTheme())
}