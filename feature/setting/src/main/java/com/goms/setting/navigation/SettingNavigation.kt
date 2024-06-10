package com.goms.setting.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.goms.setting.SettingRoute
import com.goms.setting.WithdrawalRoute

const val settingRoute = "setting_route"
const val withdrawalRoute = "withdrawal_route"
fun NavController.navigateToSettingScreen(navOptions: NavOptions? = null) {
    this.navigate(settingRoute, navOptions)
}

fun NavGraphBuilder.settingScreen(
    onLogoutSuccess: () -> Unit,
    onBackClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
    onPasswordCheck: () -> Unit,
    onUpdateAlarm: (String) -> Unit,
    onThemeSelect: () -> Unit,
    onWithdrawalClick: () -> Unit
) {
    composable(route = settingRoute) {
        SettingRoute(
            onLogoutSuccess = onLogoutSuccess,
            onBackClick = onBackClick,
            onErrorToast = onErrorToast,
            onPasswordCheck = onPasswordCheck,
            onUpdateAlarm = onUpdateAlarm,
            onThemeSelect = onThemeSelect,
            onWithdrawalClick = onWithdrawalClick
        )
    }
}

fun NavController.navigateToWithdrawalScreen(navOptions: NavOptions? = null) {
    this.navigate(withdrawalRoute, navOptions)
}

fun NavGraphBuilder.withdrawalScreen(
    onBackClick: () -> Unit,
    onWithdrawal: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,

) {
    composable(route = withdrawalRoute) {
        WithdrawalRoute(
            onBackClick = onBackClick,
            onWithdrawal = onWithdrawal,
            onErrorToast = onErrorToast
        )
    }
}