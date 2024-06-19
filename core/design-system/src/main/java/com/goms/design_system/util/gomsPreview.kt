package com.goms.design_system.util

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.goms.design_system.theme.GomsTheme
import com.goms.design_system.theme.ThemeType

@Composable
fun gomsPreview(
    content: @Composable () -> Unit
) {
    Column {
        GomsTheme(ThemeType.DARK.value) {
            content()
        }
        GomsTheme(ThemeType.LIGHT.value) {
            content()
        }
    }
}