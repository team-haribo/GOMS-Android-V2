package com.goms.qrcode.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.goms.qrcode.QrcodeScanRoute

const val qrcodeScanRoute = "qrcode_scan_route"

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