package com.goms.qrcode

import android.Manifest
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.goms.common.result.Result
import com.goms.design_system.component.dialog.GomsOneButtonDialog
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
    onBackClick: () -> Unit,
    onError: () -> Unit,
    onSuccess: () -> Unit,
    viewModel: QrcodeViewModel = hiltViewModel(),
) {
    val outingUiState by viewModel.outingState.collectAsState()
    val cameraPermissionState: PermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    LaunchedEffect("getPermission") {
        if (!cameraPermissionState.status.isGranted && !cameraPermissionState.status.shouldShowRationale) run {
            cameraPermissionState.launchPermissionRequest()
        }
    }

    if (cameraPermissionState.status.isGranted) {
        QrcodeScanScreen(
            outingUiState = outingUiState,
            onQrcodeScan = { qrcodeData ->
                viewModel.outing(UUID.fromString(qrcodeData))
            },
            onError = onError,
            onSuccess = onSuccess,
            onBackClick = onBackClick
        )
    } else {
        onPermissionBlock()
    }
}

@androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
@Composable
fun QrcodeScanScreen(
    outingUiState: Result<Unit>,
    onQrcodeScan: (String?) -> Unit,
    onBackClick: () -> Unit,
    onError: () -> Unit,
    onSuccess: () -> Unit
) {
    var openDialog by remember { mutableStateOf(false) }

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
            QrcodeScanTopBar(onClick = onBackClick)
            Spacer(modifier = Modifier.height(224.dp))
            QrcodeScanGuide()
        }
    }

    when (outingUiState) {
        is Result.Loading -> Unit
        is Result.Success -> openDialog = true
        is Result.Error -> {
            onError()
            Toast.makeText(LocalContext.current,"네트워크 에러",Toast.LENGTH_LONG).show()
        }
    }

    if(openDialog) {
        GomsOneButtonDialog(
            openDialog = openDialog,
            onStateChange = {
                openDialog = it
            },
            title = "외출하기 성공",
            content = "7시 25분 까지 복귀해 주세요!",
            buttonText = "확인",
            onClick = onSuccess
        )
    }
}