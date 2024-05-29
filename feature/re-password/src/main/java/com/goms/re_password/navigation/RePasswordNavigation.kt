package com.goms.re_password.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.goms.re_password.PasswordCheckRoute
import com.goms.re_password.RePasswordRoute

const val passwordCheckRoute = "password_check_route"
const val rePasswordRoute = "re_password_route"

fun NavController.navigateToPasswordCheck(navOptions: NavOptions? = null) {
    this.navigate(passwordCheckRoute, navOptions)
}

fun NavGraphBuilder.passwordCheckScreen(
    onBackClick: () -> Unit,
    onRePasswordClick: () -> Unit
) {
    composable(route = passwordCheckRoute) {
        PasswordCheckRoute(
            onBackClick = onBackClick,
            onRePasswordClick = onRePasswordClick,
        )
    }
}

fun NavController.navigateToRePassword(navOptions: NavOptions? = null) {
    this.navigate(rePasswordRoute, navOptions)
}

fun NavGraphBuilder.rePasswordScreen(
    onBackClick: () -> Unit,
    onSuccessClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit
) {
    composable(route = rePasswordRoute) {
        RePasswordRoute(
            onBackClick = onBackClick,
            onSuccessClick = onSuccessClick,
            onErrorToast = onErrorToast
        )
    }
}