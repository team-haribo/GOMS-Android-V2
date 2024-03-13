package com.goms.qrcode.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.goms.qrcode.QrcodeGenerateRoute
import com.goms.qrcode.QrcodeScanRoute
import com.goms.qrcode.viewmodel.GetOutingUUIDUiState

const val qrcodeScanRoute = "qrcode_scan_route"
const val qrcodeGenerateRoute = "qrcode_generate_route"

fun NavController.navigateToQrScan(navOptions: NavOptions? = null) {
    this.navigate(qrcodeScanRoute, navOptions)
}

fun NavGraphBuilder.qrcodeScanScreen(
    onPermissionBlock: () -> Unit
) {
    composable(route = qrcodeScanRoute) {
        QrcodeScanRoute(
            onPermissionBlock = onPermissionBlock
        )
    }
}

fun NavController.navigateToQrGenerate(navOptions: NavOptions? = null) {
    this.navigate(qrcodeGenerateRoute, navOptions)
}

fun NavGraphBuilder.qrcodeGenerateScreen(
    onRemoteError: () -> Unit,
    onBackClick: () -> Unit,
    onTimerFinish: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: String?) -> Unit
) {
    composable(route = qrcodeGenerateRoute) {
        QrcodeGenerateRoute(
            onRemoteError = onRemoteError,
            onBackClick = onBackClick,
            onTimerFinish = onTimerFinish,
            onErrorToast = onErrorToast
        )
    }
}