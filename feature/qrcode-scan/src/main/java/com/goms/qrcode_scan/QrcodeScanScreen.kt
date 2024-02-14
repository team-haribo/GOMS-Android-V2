package com.goms.qrcode_scan

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.goms.design_system.theme.GomsTheme
import com.goms.qrcode_scan.component.QrcodeScanGuide
import com.goms.qrcode_scan.component.QrcodeScanPreview
import com.goms.qrcode_scan.component.QrcodeScanTopBar
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun QrcodeScanRoute(
    onPermissionBlock: () -> Unit
) {
    val cameraPermissionState: PermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    LaunchedEffect("getPermission") {
        if (!cameraPermissionState.status.isGranted && !cameraPermissionState.status.shouldShowRationale) run {
            cameraPermissionState.launchPermissionRequest()
        }
    }

    if (cameraPermissionState.status.isGranted) {
        QrcodeScanScreen()
    } else {
        onPermissionBlock()
    }
}
@androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
@Composable
fun QrcodeScanScreen() {
    GomsTheme { _, _ ->
        QrcodeScanPreview(
            context = LocalContext.current,
            onQrcodeScan = {}
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            QrcodeScanTopBar()
            Spacer(modifier = Modifier.height(224.dp))
            QrcodeScanGuide()
        }
    }
}

@Composable
@Preview(showBackground = true)
fun QrcodeScanScreenPreview() {
    QrcodeScanScreen()
}
