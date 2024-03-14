package com.goms.setting.navigation

import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.goms.setting.SettingRoute

const val settingRoute = "setting_route"
fun NavController.navigateToSettingScreen(navOptions: NavOptions? = null) {
    this.navigate(settingRoute, navOptions)
}

fun NavGraphBuilder.settingScreen(
    viewModelStoreOwner: ViewModelStoreOwner,
    onLogoutSuccess: () -> Unit,
    onBackClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: String?) -> Unit,
    onEmailCheck: () -> Unit
) {
    composable(route = settingRoute) {
        SettingRoute(
            viewModelStoreOwner = viewModelStoreOwner,
            onLogoutSuccess = onLogoutSuccess,
            onBackClick = onBackClick,
            onErrorToast = onErrorToast,
            onEmailCheck = onEmailCheck
        )
    }
}