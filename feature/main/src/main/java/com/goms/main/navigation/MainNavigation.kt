package com.goms.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.goms.main.AdminMenuRoute
import com.goms.main.LateListRoute
import com.goms.main.MainRoute
import com.goms.main.OutingStatusRoute
import com.goms.main.StudentManagementRoute
import com.goms.model.enum.Authority

const val mainRoute = "main_route"
const val outingStatusRoute = "outing_status_route"
const val lateListRoute = "late_list_route"
const val studentManagementRoute = "student_management_route"
const val adminMenuRoute = "admin_menu_route"

fun NavController.navigateToMain(navOptions: NavOptions? = null) {
    this.navigate(mainRoute, navOptions)
}

fun NavGraphBuilder.mainScreen(
    qrcodeState: String,
    onOutingStatusClick: () -> Unit,
    onLateListClick: () -> Unit,
    onQrcodeClick: (role: Authority) -> Unit,
    onSettingClick: () -> Unit,
    onAdminMenuClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit
) {
    composable(route = mainRoute) {
        MainRoute(
            qrcodeState = qrcodeState,
            onOutingStatusClick = onOutingStatusClick,
            onLateListClick = onLateListClick,
            onQrcodeClick = onQrcodeClick,
            onSettingClick = onSettingClick,
            onAdminMenuClick = onAdminMenuClick,
            onErrorToast = onErrorToast
        )
    }
}

fun NavController.navigateToOutingStatus(navOptions: NavOptions? = null) {
    this.navigate(outingStatusRoute, navOptions)
}

fun NavGraphBuilder.outingStatusScreen(
    onBackClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit
) {
    composable(route = outingStatusRoute) {
        OutingStatusRoute(
            onBackClick = onBackClick,
            onErrorToast = onErrorToast
        )
    }
}

fun NavController.navigateToLateList(navOptions: NavOptions? = null) {
    this.navigate(lateListRoute, navOptions)
}

fun NavGraphBuilder.lateListScreen(
    onBackClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit
) {
    composable(route = lateListRoute) {
        LateListRoute(
            onBackClick = onBackClick,
            onErrorToast = onErrorToast
        )
    }
}

fun NavController.navigateToStudentManagement(navOptions: NavOptions? = null) {
    this.navigate(studentManagementRoute, navOptions)
}

fun NavGraphBuilder.studentManagementScreen(
    onBackClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit
) {
    composable(route = studentManagementRoute) {
        StudentManagementRoute(
            onBackClick = onBackClick,
            onErrorToast = onErrorToast
        )
    }
}

fun NavController.navigateToAdminMenu(navOptions: NavOptions? = null) {
    this.navigate(adminMenuRoute, navOptions)
}

fun NavGraphBuilder.adminMenuScreen(
    onBackClick: () -> Unit,
    onQrCreateClick: () -> Unit,
    onStudentManagementClick: () -> Unit,
    onOutingStatusClick: () -> Unit,
    onLateListClick: () -> Unit,
    onSettingClick: () -> Unit
) {
    composable(route = adminMenuRoute) {
        AdminMenuRoute(
            onBackClick = onBackClick,
            onQrCreateClick = onQrCreateClick,
            onStudentManagementClick = onStudentManagementClick,
            onOutingStatusClick = onOutingStatusClick,
            onLateListClick = onLateListClick,
            onSettingClick = onSettingClick
        )
    }
}