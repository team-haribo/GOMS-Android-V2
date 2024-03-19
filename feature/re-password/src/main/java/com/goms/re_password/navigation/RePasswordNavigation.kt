package com.goms.re_password.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.goms.re_password.EmailCheckRoute
import com.goms.re_password.NumberRoute
import com.goms.re_password.RePasswordRoute

const val emailCheckRoute = "email_check_route"
const val numberRoute = "number_route"
const val rePasswordRoute = "re_password_route"

fun NavController.navigateToEmailCheck(navOptions: NavOptions? = null) {
    this.navigate(emailCheckRoute, navOptions)
}

fun NavGraphBuilder.emailCheckScreen(
    onBackClick: () -> Unit,
    onNumberClick: () -> Unit
) {
    composable(route = emailCheckRoute) {
        EmailCheckRoute(
            onBackClick = onBackClick,
            onNumberClick = onNumberClick
        )
    }
}

fun NavController.navigateToPasswordNumber(navOptions: NavOptions? = null) {
    this.navigate(numberRoute, navOptions)
}

fun NavGraphBuilder.passwordNumberScreen(
    onBackClick: () -> Unit,
    onRePasswordClick: () -> Unit
) {
    composable(route = numberRoute) {
        NumberRoute(
            onBackClick = onBackClick,
            onRePasswordClick = onRePasswordClick
        )
    }
}

fun NavController.navigateToRePassword(navOptions: NavOptions? = null) {
    this.navigate(rePasswordRoute, navOptions)
}

fun NavGraphBuilder.rePasswordScreen(
    onBackClick: () -> Unit,
    onSuccessClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: String?) -> Unit
) {
    composable(route = rePasswordRoute) {
        RePasswordRoute(
            onBackClick = onBackClick,
            onSuccessClick = onSuccessClick,
            onErrorToast = onErrorToast
        )
    }
}