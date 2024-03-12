package com.goms.setting.navigation

import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val settingRoute = "setting_route"
fun NavController.navigateToSettingScreen(navOptions: NavOptions? = null) {
    this.navigate(settingRoute, navOptions)
}

fun NavGraphBuilder.settingScreen(
    viewModelStoreOwner: ViewModelStoreOwner,
    onLogoutSuccess: () -> Unit,
    onBackClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: String?) -> Unit
) {
    composable(route = settingRoute) {
        com.goms.setting.SettingRoute(
            viewModelStoreOwner = viewModelStoreOwner,
            onLogoutSuccess = onLogoutSuccess,
            onBackClick = onBackClick,
            onErrorToast = onErrorToast
        )
    }
}