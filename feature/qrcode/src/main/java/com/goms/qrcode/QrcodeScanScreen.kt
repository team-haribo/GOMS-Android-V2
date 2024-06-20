package com.goms.qrcode

import android.Manifest
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.goms.design_system.component.dialog.GomsOneButtonDialog
import com.goms.design_system.theme.GomsTheme
import com.goms.design_system.theme.ThemeType
import com.goms.design_system.util.lockScreenOrientation
import com.goms.qrcode.component.QrcodeScanGuide
import com.goms.qrcode.component.QrcodeScanPreview
import com.goms.qrcode.component.QrcodeScanTopBar
import com.goms.qrcode.viewmodel.uistate.GetProfileUiState
import com.goms.qrcode.viewmodel.uistate.OutingUiState
import com.goms.qrcode.viewmodel.QrcodeViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import java.util.UUID

@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun QrcodeScanRoute(
    onPermissionBlock: () -> Unit,
    onBackClick: () -> Unit,
    onSuccess: () -> Unit,
    viewModel: QrcodeViewModel = hiltViewModel(),
) {
    val outingUiState by viewModel.outingState.collectAsStateWithLifecycle()
    val getProfileUiState by viewModel.getProfileUiState.collectAsStateWithLifecycle()
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    LaunchedEffect("getPermission") {
        if (!cameraPermissionState.status.isGranted && !cameraPermissionState.status.shouldShowRationale) {
            cameraPermissionState.launchPermissionRequest()
        }
    }

    if (cameraPermissionState.status.isGranted) {
        QrcodeScanScreen(
            outingUiState = outingUiState,
            profileUiState = getProfileUiState,
            onQrcodeScan = { qrcodeData ->
                try {
                    viewModel.outing(UUID.fromString(qrcodeData))
                } catch (e: IllegalArgumentException) {
                    viewModel.qrcodeDataError()
                }
            },
            onSuccess = onSuccess,
            onBackClick = onBackClick,
            getProfile = { viewModel.getProfile() }
        )
    } else {
        onPermissionBlock()
    }
}

@androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
@Composable
private fun QrcodeScanScreen(
    outingUiState: OutingUiState,
    profileUiState: GetProfileUiState,
    onQrcodeScan: (String?) -> Unit,
    onBackClick: () -> Unit,
    onSuccess: () -> Unit,
    getProfile: () -> Unit,
) {
    val context = LocalContext.current
    var openDialog by remember { mutableStateOf(false) }
    var dialogTitle by remember { mutableStateOf("") }
    var dialogContent by remember { mutableStateOf("") }
    var isOuting by remember { mutableStateOf(false) }

    LaunchedEffect("getProfile") {
        getProfile()
    }

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

    if (profileUiState is GetProfileUiState.Success) {
        isOuting = !profileUiState.getProfileResponseModel.isOuting
    }

    when (outingUiState) {
        is OutingUiState.Loading -> Unit
        is OutingUiState.Success ->  {
            openDialog = true
            dialogTitle = context.getString(R.string.success_qr_scan)
            dialogContent = if (isOuting) context.getString(R.string.start_outing) else context.getString(R.string.back_outing)
        }

        is OutingUiState.BadRequest -> {
            openDialog = true
            dialogTitle = context.getString(R.string.fail_qr_scan)
            dialogContent = context.getString(R.string.error_blacklist)
        }

        is OutingUiState.Error -> {
            openDialog = true
            dialogTitle = context.getString(R.string.fail_qr_scan)
            dialogContent = context.getString(R.string.error_outing)
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
            buttonText = stringResource(id = R.string.check),
            onClick = onSuccess
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun QrcodeScanScreenPreview() {
    GomsTheme(ThemeType.SYSTEM.value) {
        QrcodeScanScreen(
            outingUiState = OutingUiState.Loading,
            profileUiState = GetProfileUiState.Loading,
            onQrcodeScan = {},
            onBackClick = {},
            onSuccess = {},
        ) {}
    }
}