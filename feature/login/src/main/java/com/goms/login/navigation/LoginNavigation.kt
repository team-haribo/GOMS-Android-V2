package com.goms.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.goms.login.EmailLoginRoute
import com.goms.login.LoginRoute
import com.goms.login.NumberLoginRoute

const val loginRoute = "login_route"
const val emailLoginRoute = "email_login_route"
const val numberLoginRoute = "number_login_route"

fun NavController.navigateToLogin(navOptions: NavOptions? = null) {
    this.navigate(loginRoute, navOptions)
}

fun NavGraphBuilder.loginScreen(onSignUpClick: () -> Unit) {
    composable(route = loginRoute) {
        LoginRoute(
            onSignUpClick = onSignUpClick
        )
    }
}

fun NavController.navigateToEmailLogin(navOptions: NavOptions? = null) {
    this.navigate(emailLoginRoute, navOptions)
}

fun NavGraphBuilder.emailLoginScreen(
    onLoginClick: () -> Unit,
    onNumberLoginClick: () -> Unit
) {
    composable(route = emailLoginRoute) {
        EmailLoginRoute(
            onLoginClick = onLoginClick,
            onNumberLoginClick = onNumberLoginClick
        )
    }
}

fun NavController.navigateToNumberLogin(navOptions: NavOptions? = null) {
    this.navigate(numberLoginRoute, navOptions)
}

fun NavGraphBuilder.numberLoginScreen() {
    composable(route = numberLoginRoute) {
        NumberLoginRoute()
    }
}