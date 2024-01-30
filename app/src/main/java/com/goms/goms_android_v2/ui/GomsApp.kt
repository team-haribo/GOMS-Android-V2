package com.goms.goms_android_v2.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import com.goms.design_system.theme.GomsTheme
import com.goms.goms_android_v2.MainActivityUiState
import com.goms.goms_android_v2.navigation.GomsNavHost
import com.goms.login.navigation.loginRoute
import com.goms.main.navigation.mainRoute

@Composable
fun GomsApp(
    windowSizeClass: WindowSizeClass,
    appState: GomsAppState = rememberBitgoeulAppState(
        windowSizeClass = windowSizeClass
    ),
    uiState: MainActivityUiState
) {
    GomsTheme { _,_ ->
        GomsNavHost(
            appState = appState,
            startDestination = when (uiState) {
                is MainActivityUiState.Success -> mainRoute
                is MainActivityUiState.Error -> loginRoute
                else -> loginRoute
            }
        )
    }
}