package com.goms.main.navigation

import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.goms.main.MainRoute
import com.goms.main.OutingStatusRoute

const val mainRoute = "main_route"
const val outingStatusRoute = "outing_status_route"

fun NavController.navigateToMain(navOptions: NavOptions? = null) {
    this.navigate(mainRoute, navOptions)
}

fun NavGraphBuilder.mainScreen(
    viewModelStoreOwner: ViewModelStoreOwner,
    onOutingStatusClick: () -> Unit
) {
    composable(route = mainRoute) {
        MainRoute(
            viewModelStoreOwner = viewModelStoreOwner,
            onOutingStatusClick = onOutingStatusClick
        )
    }
}

fun NavController.navigateToOutingStatus(navOptions: NavOptions? = null) {
    this.navigate(outingStatusRoute, navOptions)
}

fun NavGraphBuilder.outingStatusScreen(
    viewModelStoreOwner: ViewModelStoreOwner,
    onBackClick: () -> Unit
) {
    composable(route = outingStatusRoute) {
        OutingStatusRoute(
            viewModelStoreOwner = viewModelStoreOwner,
            onBackClick = onBackClick
        )
    }
}