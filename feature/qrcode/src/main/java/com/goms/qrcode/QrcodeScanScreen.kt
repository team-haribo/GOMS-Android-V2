package com.goms.qrcode

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
import androidx.hilt.navigation.compose.hiltViewModel
import com.goms.design_system.theme.GomsTheme
import com.goms.qrcode.component.QrcodeScanGuide
import com.goms.qrcode.component.QrcodeScanPreview
import com.goms.qrcode.component.QrcodeScanTopBar
import com.goms.qrcode.viewmodel.QrcodeViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import java.util.UUID

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun QrcodeScanRoute(
    onPermissionBlock: () -> Unit,
    viewModel: QrcodeViewModel = hiltViewModel(),
) {
    val cameraPermissionState: PermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    LaunchedEffect("getPermission") {
        if (!cameraPermissionState.status.isGranted && !cameraPermissionState.status.shouldShowRationale) run {
            cameraPermissionState.launchPermissionRequest()
        }
    }

    if (cameraPermissionState.status.isGranted) {
        QrcodeScanScreen(
            onQrcodeScan = { qrcodeData ->
                viewModel.outing(UUID.fromString(qrcodeData))
            }
        )
    } else {
        onPermissionBlock()
    }
}

@androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
@Composable
fun QrcodeScanScreen(
    onQrcodeScan: (String?) -> Unit
) {
    GomsTheme { _, _ ->
        QrcodeScanPreview(
            context = LocalContext.current,
            onQrcodeScan = { qrcodeData ->
                onQrcodeScan(qrcodeData)
            }
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