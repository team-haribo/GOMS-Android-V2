package com.goms.qrcode

import android.Manifest
import android.content.pm.ActivityInfo
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.goms.design_system.component.dialog.GomsOneButtonDialog
import com.goms.design_system.util.lockScreenOrientation
import com.goms.qrcode.component.QrcodeScanGuide
import com.goms.qrcode.component.QrcodeScanPreview
import com.goms.qrcode.component.QrcodeScanTopBar
import com.goms.qrcode.viewmodel.OutingUiState
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
    onErrorToast: (throwable: Throwable?, message: String?) -> Unit
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
            onBackClick = onBackClick,
            onErrorToast = onErrorToast
        )
    } else {
        onPermissionBlock()
    }
}

@androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
@Composable
fun QrcodeScanScreen(
    outingUiState: OutingUiState,
    onQrcodeScan: (String?) -> Unit,
    onBackClick: () -> Unit,
    onError: () -> Unit,
    onSuccess: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: String?) -> Unit
) {
    var openDialog by remember { mutableStateOf(false) }
    var dialogTitle by remember { mutableStateOf("") }
    var dialogContent by remember { mutableStateOf("") }

    lockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    QrcodeScanPreview(
        onQrcodeScan = { qrcodeData ->
            onQrcodeScan(qrcodeData)
        }
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        QrcodeScanTopBar(onClick = onBackClick)
        Spacer(modifier = Modifier.weight(2f))
        QrcodeScanGuide()
        Spacer(modifier = Modifier.weight(3f))
    }

    when (outingUiState) {
        is OutingUiState.Loading -> Unit
        is OutingUiState.Success ->  {
            openDialog = true
            dialogTitle = "QR코드 스캔 성공"
            dialogContent = "외출을 시작해요!\n7시 25분까지는 반으로 돌아와야 해요!"
        }
        is OutingUiState.BadRequest -> {
            openDialog = true
            dialogTitle = "QR코드 스캔 실패"
            dialogContent = "외출에 실패했어요 ㅠ\n외출 금지 상태 이거나 잘못된 QR코드예요."
        }

        is OutingUiState.Error -> {
            openDialog = true
            dialogTitle = "QR코드 스캔 실패"
            dialogContent = "예기치 못한 오류가 발생했어요.\n네트워크 상태를 확인후 다시 시도해 주세요."
        }
    }

    if (openDialog) {
        GomsOneButtonDialog(
            openDialog = openDialog,
            onStateChange = {
                openDialog = it
            },
            title = dialogTitle,
            content = dialogContent,
            buttonText = "확인",
            onClick = onSuccess
        )
    }
}