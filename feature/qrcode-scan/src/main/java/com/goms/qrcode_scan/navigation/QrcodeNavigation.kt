package com.goms.qrcode_scan.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.goms.qrcode_scan.QrcodeScanRoute

const val qrcodeScanRoute = "qrcode_scan_route"

fun NavController.navigateToQrScan(navOptions: NavOptions? = null) {
    this.navigate(qrcodeScanRoute, navOptions)
}

fun NavGraphBuilder.qrcodeScanScreen(
    onPermissionBlock: () -> Unit
) {
    composable(route = qrcodeScanRoute) {
<<<<<<< HEAD
        QrcodeScanRoute(
            onPermissionBlock = onPermissionBlock
        )
=======
        QrcodeScanRoute(onPermissionBlock = onPermissionBlock)
>>>>>>> 628575a (:memo: :: Add nav QrcodeScanScreen.kt)
    }
}