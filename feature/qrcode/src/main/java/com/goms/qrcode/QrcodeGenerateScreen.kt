package com.goms.qrcode

import android.content.pm.ActivityInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.goms.design_system.component.shimmer.shimmerEffect
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.util.lockScreenOrientation
import com.goms.model.enum.Authority
import com.goms.qrcode.component.QrcodeGenerateText
import com.goms.qrcode.component.QrcodeGenerateTimer
import com.goms.qrcode.util.QrcodeGenerator
import com.goms.qrcode.viewmodel.uistate.GetOutingUUIDUiState
import com.goms.qrcode.viewmodel.QrcodeViewModel
import com.goms.ui.GomsRoleBackButton

@Composable
internal fun QrcodeGenerateRoute(
    onRemoteError: () -> Unit,
    viewModel: QrcodeViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onTimerFinish: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: String?) -> Unit
) {
    val role by viewModel.role.collectAsStateWithLifecycle(initialValue = "")
    val getOutingUUIDUiState by viewModel.getOutingUUIDState.collectAsStateWithLifecycle()

    QrcodeGenerateScreen(
        role = if (role.isNotBlank()) Authority.valueOf(role) else Authority.ROLE_STUDENT,
        getOutingUUIDUiState = getOutingUUIDUiState,
        onQrCreate = {
            viewModel.getOutingUUID()
        },
        onBackClick = onBackClick,
        onRemoteError = onRemoteError,
        onTimerFinish = onTimerFinish,
        onErrorToast = onErrorToast
    )
}

@Composable
private fun QrcodeGenerateScreen(
    role: Authority,
    getOutingUUIDUiState: GetOutingUUIDUiState,
    onBackClick: () -> Unit,
    onQrCreate: () -> Unit,
    onRemoteError: () -> Unit,
    onTimerFinish: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: String?) -> Unit
) {
    LaunchedEffect("qr create") {
        onQrCreate()
    }

    lockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colors.BACKGROUND)
            .navigationBarsPadding()
            .statusBarsPadding()
    ) {
        GomsRoleBackButton(role = role) {
            onBackClick()
        }
        QrcodeGenerateText(Modifier)
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(0.55f))
            when (getOutingUUIDUiState) {
                GetOutingUUIDUiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .size(200.dp)
                            .shimmerEffect(
                                color = colors.WHITE,
                                shape = RoundedCornerShape(12.dp)
                            )
                    )
                }
                is GetOutingUUIDUiState.Success -> {
                    val data = getOutingUUIDUiState.getOutingUUIDResponseModel

                    Image(
                        painter = QrcodeGenerator(content = data.outingUUID),
                        contentDescription = "outing qrcode image"
                    )
                }
                is GetOutingUUIDUiState.Error -> {
                    onRemoteError()
                    onErrorToast(getOutingUUIDUiState.exception, null)
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            QrcodeGenerateTimer(
                onTimerFinish = onTimerFinish
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}