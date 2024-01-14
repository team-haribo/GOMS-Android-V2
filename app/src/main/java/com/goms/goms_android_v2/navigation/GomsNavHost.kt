package com.goms.goms_android_v2.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.goms.goms_android_v2.ui.GomsAppState
import com.goms.login.navigation.emailLoginScreen
import com.goms.login.navigation.loginRoute
import com.goms.login.navigation.loginScreen
import com.goms.login.navigation.navigateToEmailLogin
import com.goms.login.navigation.navigateToLogin

@Composable
fun GomsNavHost(
    appState: GomsAppState,
    modifier: Modifier = Modifier,
    startDestination: String = loginRoute
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        loginScreen(
            onEmailLoginClick = navController::navigateToEmailLogin
        )
        emailLoginScreen(
            onLoginClick = navController::navigateToLogin,
            onNumberLoginClick = navController::navigateToLogin
        )
    }
}