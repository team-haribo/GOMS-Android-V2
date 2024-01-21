package com.goms.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.goms.login.LoginRoute

const val loginRoute = "login_route"

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