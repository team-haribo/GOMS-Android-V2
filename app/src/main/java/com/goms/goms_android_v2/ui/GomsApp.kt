package com.goms.goms_android_v2.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import com.goms.design_system.theme.GomsTheme
import com.goms.goms_android_v2.navigation.GomsNavHost

@Composable
fun GomsApp(
    windowSizeClass: WindowSizeClass,
    appState: GomsAppState = rememberBitgoeulAppState(
        windowSizeClass = windowSizeClass
    )
) {
    GomsTheme { _,_ ->
        GomsNavHost(appState = appState)
    }
}