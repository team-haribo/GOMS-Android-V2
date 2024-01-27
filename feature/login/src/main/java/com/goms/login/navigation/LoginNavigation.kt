package com.goms.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.goms.login.InputLoginRoute
import com.goms.login.LoginRoute

const val loginRoute = "login_route"
const val InputLoginRoute = "Input_login_route"

fun NavController.navigateToLogin(navOptions: NavOptions? = null) {
    this.navigate(loginRoute, navOptions)
}

fun NavGraphBuilder.loginScreen(
    onSignUpClick: () -> Unit,
    onInputLoginClick: () -> Unit
) {
    composable(route = loginRoute) {
        LoginRoute(
            onSignUpClick = onSignUpClick,
            onInputLoginClick = onInputLoginClick
        )
    }
}

fun NavController.navigateToInputLogin(navOptions: NavOptions? = null) {
    this.navigate(InputLoginRoute, navOptions)
}

fun NavGraphBuilder.inputLoginScreen(
    onBackClick: () -> Unit,
    onMainClick: () -> Unit
) {
    composable(route = InputLoginRoute) {
        InputLoginRoute(
            onBackClick = onBackClick,
            onMainClick = onMainClick
        )
    }
}