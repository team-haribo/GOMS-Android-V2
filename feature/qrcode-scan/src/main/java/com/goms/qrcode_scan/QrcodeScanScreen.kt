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
<<<<<<< HEAD
import androidx.hilt.navigation.compose.hiltViewModel
=======
>>>>>>> 689fa8d (:memo: :: Add get permission camera)
import com.goms.design_system.theme.GomsTheme
import com.goms.qrcode_scan.component.QrcodeScanGuide
import com.goms.qrcode_scan.component.QrcodeScanPreview
import com.goms.qrcode_scan.component.QrcodeScanTopBar
<<<<<<< HEAD
import com.goms.qrcode_scan.viewmodel.QrcodeViewModel
=======
>>>>>>> 689fa8d (:memo: :: Add get permission camera)
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
<<<<<<< HEAD
import java.util.UUID
=======
>>>>>>> 689fa8d (:memo: :: Add get permission camera)

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun QrcodeScanRoute(
<<<<<<< HEAD
    onPermissionBlock: () -> Unit,
    viewModel: QrcodeViewModel = hiltViewModel(),
=======
    onPermissionBlock: () -> Unit
>>>>>>> 689fa8d (:memo: :: Add get permission camera)
) {
    val cameraPermissionState: PermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    LaunchedEffect("getPermission") {
        if (!cameraPermissionState.status.isGranted && !cameraPermissionState.status.shouldShowRationale) run {
            cameraPermissionState.launchPermissionRequest()
        }
    }

    if (cameraPermissionState.status.isGranted) {
<<<<<<< HEAD
        QrcodeScanScreen(
            onQrcodeScan = { qrcodeData ->
                viewModel.outing(UUID.fromString(qrcodeData))
            }
        )
=======
        QrcodeScanScreen()
>>>>>>> 689fa8d (:memo: :: Add get permission camera)
    } else {
        onPermissionBlock()
    }
}
<<<<<<< HEAD

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
=======
@Composable
fun QrcodeScanScreen() {
    GomsTheme { _, _ ->
        QrcodeScanPreview(context = LocalContext.current)
>>>>>>> 689fa8d (:memo: :: Add get permission camera)
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
<<<<<<< HEAD
=======

@Composable
@Preview(showBackground = true)
fun QrcodeScanScreenPreview() {
    QrcodeScanScreen()
}
>>>>>>> 689fa8d (:memo: :: Add get permission camera)
