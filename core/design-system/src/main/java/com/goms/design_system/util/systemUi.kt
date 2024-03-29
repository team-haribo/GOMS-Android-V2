package com.goms.design_system.util

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun ApplySystemUi(isDarkTheme: Boolean) {
    val view = LocalView.current
    val context = view.context as? Activity ?: return
    val color = if (isDarkTheme) Color(0xFF0D0D0D) else Color(0xFFFFFFFF)

    WindowCompat.setDecorFitsSystemWindows(context.window, false)
    val insetsController = WindowCompat.getInsetsController(context.window, view)
    insetsController.isAppearanceLightStatusBars = !isDarkTheme
    context.window.statusBarColor = color.toArgb()
    context.window.navigationBarColor = color.toArgb()
}