package com.goms.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.goms.login.EmailRoute
import com.goms.login.LoginRoute

const val loginRoute = "login_route"
const val emailLoginRoute = "email_login_Route"

fun NavController.navigateToLogin(navOptions: NavOptions? = null) {
    this.navigate(loginRoute, navOptions)
}

fun NavGraphBuilder.loginScreen(onEmailLoginClick: () -> Unit) {
    composable(route = loginRoute) {
        LoginRoute(
            onEmailLoginClick = onEmailLoginClick
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
        EmailRoute(
            onLoginClick = onLoginClick,
            onNumberLoginClick = onNumberLoginClick
        )
    }
}