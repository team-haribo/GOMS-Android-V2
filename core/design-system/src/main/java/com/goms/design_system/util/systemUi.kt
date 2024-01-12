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

    val statusBarColor = Color.Transparent
    val navigationBarColor = if (isDarkTheme) Color.Black else Color.White

    WindowCompat.setDecorFitsSystemWindows(context.window, false)
    val insetsController = WindowCompat.getInsetsController(context.window, view)
    insetsController.isAppearanceLightStatusBars = isDarkTheme
    context.window.statusBarColor = statusBarColor.toArgb()
    context.window.navigationBarColor = navigationBarColor.toArgb()
}