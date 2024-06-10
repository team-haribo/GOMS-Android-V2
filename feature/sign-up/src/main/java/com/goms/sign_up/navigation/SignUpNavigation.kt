package com.goms.sign_up.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.goms.sign_up.NumberRoute
import com.goms.sign_up.PasswordRoute
import com.goms.sign_up.SignUpRoute

const val signUpRoute = "sign_up_route"
const val numberRoute = "number_route"
const val passwordRoute = "password_route"

fun NavController.navigateToSignUp(navOptions: NavOptions? = null) {
    this.navigate(signUpRoute, navOptions)
}

fun NavGraphBuilder.signUpScreen(
    onBackClick: () -> Unit,
    onNumberClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit
) {
    composable(route = signUpRoute) {
        SignUpRoute(
            onBackClick = onBackClick,
            onNumberClick = onNumberClick,
            onErrorToast = onErrorToast
        )
    }
}

fun NavController.navigateToNumber(navOptions: NavOptions? = null) {
    this.navigate(numberRoute, navOptions)
}

fun NavGraphBuilder.numberScreen(
    onBackClick: () -> Unit,
    onPasswordClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit
) {
    composable(route = numberRoute) {
        NumberRoute(
            onBackClick = onBackClick,
            onPasswordClick = onPasswordClick,
            onErrorToast = onErrorToast
        )
    }
}

fun NavController.navigateToPassword(navOptions: NavOptions? = null) {
    this.navigate(passwordRoute, navOptions)
}

fun NavGraphBuilder.passwordScreen(
    onBackClick: () -> Unit,
    onLoginClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit
) {
    composable(route = passwordRoute) {
        PasswordRoute(
            onBackClick = onBackClick,
            onLoginClick = onLoginClick,
            onErrorToast = onErrorToast
        )
    }
}
