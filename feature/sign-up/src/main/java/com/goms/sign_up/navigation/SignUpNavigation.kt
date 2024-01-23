package com.goms.sign_up.navigation

import androidx.lifecycle.ViewModelStoreOwner
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
    viewModelStoreOwner: ViewModelStoreOwner,
    onBackClick: () -> Unit,
    onNumberClick: () -> Unit
) {
    composable(route = signUpRoute) {
        SignUpRoute(
            viewModelStoreOwner = viewModelStoreOwner,
            onBackClick = onBackClick,
            onNumberClick = onNumberClick
        )
    }
}

fun NavController.navigateToNumber(navOptions: NavOptions? = null) {
    this.navigate(numberRoute, navOptions)
}

fun NavGraphBuilder.numberScreen(
    viewModelStoreOwner: ViewModelStoreOwner,
    onBackClick: () -> Unit,
    onPasswordClick: () -> Unit
) {
    composable(route = numberRoute) {
        NumberRoute(
            viewModelStoreOwner = viewModelStoreOwner,
            onBackClick = onBackClick,
            onPasswordClick = onPasswordClick
        )
    }
}

fun NavController.navigateToPassword(navOptions: NavOptions? = null) {
    this.navigate(passwordRoute, navOptions)
}

fun NavGraphBuilder.passwordScreen(
    viewModelStoreOwner: ViewModelStoreOwner,
    onBackClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    composable(route = passwordRoute) {
        PasswordRoute(
            viewModelStoreOwner = viewModelStoreOwner,
            onBackClick = onBackClick,
            onLoginClick = onLoginClick
        )
    }
}
