package com.goms.goms_android_v2.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.goms.design_system.theme.GomsTheme
import com.goms.goms_android_v2.MainActivityUiState
import com.goms.goms_android_v2.MainActivityViewModel
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
    uiState: MainActivityUiState,
    viewModel: MainActivityViewModel = hiltViewModel(),
) {
    val themeState by viewModel.themeState.collectAsState()
    val qrcodeState by viewModel.qrcodeState.collectAsState()

    GomsTheme(themeMode = themeState) { _, _ ->
        CompositionLocalProvider {
            GomsNavHost(
                appState = appState,
                qrcodeState = qrcodeState,
                onLogout = onLogout,
                onThemeSelect = { viewModel.getTheme() },
                startDestination = when (uiState) {
                    is MainActivityUiState.Success -> mainRoute
                    is MainActivityUiState.Error -> loginRoute
                    else -> loginRoute
                }
            )
        }
    }
}