package com.goms.main.navigation

import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.goms.main.LateListRoute
import com.goms.main.MainRoute
import com.goms.main.OutingStatusRoute
import com.goms.main.StudentManagementRoute

const val mainRoute = "main_route"
const val outingStatusRoute = "outing_status_route"
const val lateListRoute = "late_list_route"
const val studentManagementRoute = "student_management_route"

fun NavController.navigateToMain(navOptions: NavOptions? = null) {
    this.navigate(mainRoute, navOptions)
}

fun NavGraphBuilder.mainScreen(
    viewModelStoreOwner: ViewModelStoreOwner,
    onOutingStatusClick: () -> Unit,
    onLateListClick: () -> Unit,
    onStudentManagementClick: () -> Unit,
    onQrcodeClick: () -> Unit
) {
    composable(route = mainRoute) {
        MainRoute(
            viewModelStoreOwner = viewModelStoreOwner,
            onOutingStatusClick = onOutingStatusClick,
            onLateListClick = onLateListClick,
            onStudentManagementClick = onStudentManagementClick,
            onQrcodeClick = onQrcodeClick
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

fun NavController.navigateToLateList(navOptions: NavOptions? = null) {
    this.navigate(lateListRoute, navOptions)
}

fun NavGraphBuilder.lateListScreen(
    viewModelStoreOwner: ViewModelStoreOwner,
    onBackClick: () -> Unit
) {
    composable(route = lateListRoute) {
        LateListRoute(
            viewModelStoreOwner = viewModelStoreOwner,
            onBackClick = onBackClick
        )
    }
}

fun NavController.navigateToStudentManagement(navOptions: NavOptions? = null) {
    this.navigate(studentManagementRoute, navOptions)
}

fun NavGraphBuilder.studentManagementScreen(
    viewModelStoreOwner: ViewModelStoreOwner,
    onBackClick: () -> Unit
) {
    composable(route = studentManagementRoute) {
        StudentManagementRoute(
            viewModelStoreOwner = viewModelStoreOwner,
            onBackClick = onBackClick
        )
    }
}