package com.goms.goms_android_v2.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import com.goms.goms_android_v2.MainActivityUiState
import com.goms.goms_android_v2.navigation.GomsNavHost
import com.goms.login.navigation.loginRoute
import com.goms.main.navigation.mainRoute

@Composable
fun GomsApp(
    windowSizeClass: WindowSizeClass,
    appState: GomsAppState = rememberGomsAppState(
        windowSizeClass = windowSizeClass
    ),
    onLogout: () -> Unit,
    uiState: MainActivityUiState
) {
    GomsNavHost(
        appState = appState,
        onLogout = onLogout,
        startDestination = when (uiState) {
            is MainActivityUiState.Success -> mainRoute
            is MainActivityUiState.Error -> loginRoute
            else -> loginRoute
        }
    )
}