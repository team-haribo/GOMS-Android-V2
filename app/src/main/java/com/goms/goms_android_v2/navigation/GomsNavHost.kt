package com.goms.goms_android_v2.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.compose.NavHost
import com.goms.goms_android_v2.ui.GomsAppState
import com.goms.login.navigation.inputLoginScreen
import com.goms.login.navigation.loginRoute
import com.goms.login.navigation.loginScreen
import com.goms.login.navigation.navigateToInputLogin
import com.goms.login.navigation.navigateToLogin
import com.goms.main.navigation.mainScreen
import com.goms.main.navigation.navigateToMain
import com.goms.main.navigation.navigateToOutingStatus
import com.goms.main.navigation.outingStatusScreen
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
    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current)
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        loginScreen(
            onSignUpClick = navController::navigateToSignUp,
            onInputLoginClick = navController::navigateToInputLogin
        )
        inputLoginScreen(
            onBackClick = navController::popBackStack,
            onMainClick = { appState.navigateToTopLevelDestination(TopLevelDestination.MAIN) }
        )
        signUpScreen(
            viewModelStoreOwner = viewModelStoreOwner,
            onBackClick = navController::popBackStack,
            onNumberClick = navController::navigateToNumber
        )
        numberScreen(
            viewModelStoreOwner = viewModelStoreOwner,
            onBackClick = navController::popBackStack,
            onPasswordClick = navController::navigateToPassword
        )
        passwordScreen(
            viewModelStoreOwner = viewModelStoreOwner,
            onBackClick = navController::popBackStack,
            onLoginClick = { appState.navigateToTopLevelDestination(TopLevelDestination.LOGIN) }
        )
        mainScreen(
            viewModelStoreOwner = viewModelStoreOwner,
            onOutingStatusClick = navController::navigateToOutingStatus
        )
        outingStatusScreen(
            viewModelStoreOwner = viewModelStoreOwner,
            onBackClick = navController::popBackStack
        )
    }
}