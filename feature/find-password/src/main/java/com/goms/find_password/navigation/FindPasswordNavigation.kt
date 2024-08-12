package com.goms.find_password.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.goms.find_password.EmailCheckRoute
import com.goms.find_password.FindPasswordRoute
import com.goms.find_password.PasswordNumberRoute

const val emailCheckRoute = "email_check_route"
const val passwordNumberRoute = "password_number_route"
const val findPasswordRoute = "find_password_route"

fun NavController.navigateToEmailCheck(navOptions: NavOptions? = null) {
    this.navigate(emailCheckRoute, navOptions)
}

fun NavGraphBuilder.emailCheckScreen(
    onBackClick: () -> Unit,
    onNumberClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit
) {
    composable(route = emailCheckRoute) {
        EmailCheckRoute(
            onBackClick = onBackClick,
            onNumberClick = onNumberClick,
            onErrorToast = onErrorToast
        )
    }
}

fun NavController.navigateToPasswordNumber(navOptions: NavOptions? = null) {
    this.navigate(passwordNumberRoute, navOptions)
}

fun NavGraphBuilder.passwordNumberScreen(
    onBackClick: () -> Unit,
    onFindPasswordClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit
) {
    composable(route = passwordNumberRoute) {
        PasswordNumberRoute(
            onBackClick = onBackClick,
            onFindPasswordClick = onFindPasswordClick,
            onErrorToast = onErrorToast
        )
    }
}

fun NavController.navigateToFindPassword(navOptions: NavOptions? = null) {
    this.navigate(findPasswordRoute, navOptions)
}

fun NavGraphBuilder.findPasswordScreen(
    onBackClick: () -> Unit,
    onSuccessClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit
) {
    composable(route = findPasswordRoute) {
        FindPasswordRoute(
            onBackClick = onBackClick,
            onSuccessClick = onSuccessClick,
            onErrorToast = onErrorToast
        )
    }
}