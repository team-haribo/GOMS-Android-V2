package com.goms.qrcode

import android.Manifest
import android.content.ContentValues.TAG
import android.content.pm.ActivityInfo
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.goms.design_system.theme.GomsTheme
import com.goms.design_system.theme.ThemeType
import com.goms.design_system.util.DevicePreviews
import com.goms.design_system.util.lockScreenOrientation
import com.goms.model.util.ResourceKeys
import com.goms.qrcode.component.QrcodeResultDialog
import com.goms.qrcode.component.QrcodeScanGuide
import com.goms.qrcode.component.QrcodeScanTopBar
import com.goms.qrcode.component.QrcodeScanView
import com.goms.qrcode.viewmodel.QrcodeViewModel
import com.goms.qrcode.viewmodel.uistate.GetProfileUiState
import com.goms.qrcode.viewmodel.uistate.OutingUiState
import com.goms.ui.TrackScreenViewEvent
import com.goms.ui.isAfterReturnTime
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
            getProfile = viewModel::getProfile
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
    var isPlaying by remember { mutableStateOf(false) }
    var dialogRawId by remember { mutableIntStateOf(0) }
    var dialogTitle by remember { mutableStateOf(ResourceKeys.EMPTY) }
    var dialogDescription by remember { mutableStateOf(ResourceKeys.EMPTY) }
    var dialogButtonText by remember { mutableStateOf(R.string.check) }
    var isOuting by remember { mutableStateOf(false) }

    val lifecycleOwner = context as? LifecycleOwner
        ?: throw IllegalStateException("Context is not a LifecycleOwner")

    LaunchedEffect("getProfile") {
        getProfile()
    }

    lockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    QrcodeScanView(
        lifecycleOwner = lifecycleOwner,
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
        is OutingUiState.Success -> {
            openDialog = true
            when {
                isAfterReturnTime() -> {
                    dialogRawId = com.goms.design_system.R.raw.outing_failed
                    dialogTitle = context.getString(R.string.late_outing)
                    dialogDescription = context.getString(R.string.late_outing_description)
                }

                isOuting -> {
                    isPlaying = true
                    dialogRawId = com.goms.design_system.R.raw.outing_success
                    dialogTitle = context.getString(R.string.success_qrcode)
                    dialogDescription = context.getString(R.string.success_qrcode_description)
                }

                else -> {
                    dialogRawId = com.goms.design_system.R.raw.return_success
                    dialogTitle = context.getString(R.string.success_return)
                    dialogDescription = context.getString(R.string.success_return_description)
                }
            }
        }

        is OutingUiState.BadRequest -> {
            openDialog = true
            dialogRawId = com.goms.design_system.R.raw.blacklist
            dialogTitle = context.getString(R.string.no_outing)
            dialogDescription = context.getString(R.string.no_outing_description)
            dialogButtonText = R.string.back_home
        }

        is OutingUiState.Error -> {
            openDialog = true
            dialogRawId = com.goms.design_system.R.raw.outing_failed
            dialogTitle = context.getString(R.string.fail_qrcode)
            dialogDescription = context.getString(R.string.fail_qrcode_description)
            dialogButtonText = R.string.back_camera
        }
    }
    if (openDialog) {
        QrcodeResultDialog(
            openDialog = openDialog,
            onStateChange = {
                openDialog = it
            },
            rawId = dialogRawId,
            isPlaying = isPlaying,
            title = dialogTitle,
            description = dialogDescription,
            buttonText = stringResource(id = dialogButtonText),
            onClick = {
                if (outingUiState !is OutingUiState.Error) onSuccess()
            }
        )
    }
    TrackScreenViewEvent(screenName = stringResource(id = R.string.qrcode_scan_screen))
}

@DevicePreviews
@Composable
private fun QrcodeScanScreenPreview() {
    GomsTheme(ThemeType.SYSTEM.value) {
        QrcodeScanScreen(
            outingUiState = OutingUiState.Loading,
            profileUiState = GetProfileUiState.Loading,
            onQrcodeScan = {},
            onBackClick = {},
            onSuccess = {},
            getProfile = {},
        )
    }
}