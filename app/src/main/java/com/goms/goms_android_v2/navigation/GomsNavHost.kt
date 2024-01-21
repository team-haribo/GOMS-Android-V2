package com.goms.goms_android_v2.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.goms.goms_android_v2.ui.GomsAppState
import com.goms.login.navigation.loginRoute
import com.goms.login.navigation.loginScreen
import com.goms.sign_up.navigation.navigateToNumber
import com.goms.sign_up.navigation.navigateToPassword
import com.goms.sign_up.navigation.navigateToSignUp
import com.goms.sign_up.navigation.numberScreen
import com.goms.sign_up.navigation.passwordScreen
import com.goms.sign_up.navigation.signUpScreen

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
            onSignUpClick = navController::navigateToSignUp
        )
        signUpScreen(
            onBackClick = navController::popBackStack,
            onNumberClick = navController::navigateToNumber
        )
        numberScreen(
            onBackClick = navController::popBackStack,
            onPasswordClick = navController::navigateToPassword
        )
        passwordScreen(
            onBackClick = navController::popBackStack
        )
    }
}