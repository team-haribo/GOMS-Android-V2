package com.goms.setting.navigation

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
    onLogoutSuccess: () -> Unit,
    onBackClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: String?) -> Unit,
    onPasswordCheck: () -> Unit,
    onUpdateAlarm: (String) -> Unit,
    onThemeSelect: () -> Unit,
) {
    composable(route = settingRoute) {
        SettingRoute(
            onLogoutSuccess = onLogoutSuccess,
            onBackClick = onBackClick,
            onErrorToast = onErrorToast,
            onPasswordCheck = onPasswordCheck,
            onUpdateAlarm = onUpdateAlarm,
            onThemeSelect = onThemeSelect
        )
    }
}