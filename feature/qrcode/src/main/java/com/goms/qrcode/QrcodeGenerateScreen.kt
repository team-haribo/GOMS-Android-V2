package com.goms.qrcode

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.goms.design_system.component.button.GomsBackButton
import com.goms.qrcode.component.QrcodeGenerateText
import com.goms.qrcode.component.QrcodeGenerateTimer
import com.goms.qrcode.util.QrcodeGenerator
import com.goms.qrcode.viewmodel.GetOutingUUIDUiState
import com.goms.qrcode.viewmodel.QrcodeViewModel
import java.util.UUID

@Composable
fun QrcodeGenerateRoute(
    onRemoteError: () -> Unit,
    viewModel: QrcodeViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onTimerFinish: () -> Unit,
) {
    val getOutingUUIDUiState by viewModel.getOutingUUIDState.collectAsStateWithLifecycle()

    QrcodeGenerateScreen(
        getOutingUUIDUiState = getOutingUUIDUiState,
        onQrCreate = {
            viewModel.getOutingUUID()
        },
        onBackClick = onBackClick,
        onRemoteError = onRemoteError,
        onTimerFinish = onTimerFinish
    )
}

@Composable
fun QrcodeGenerateScreen(
    getOutingUUIDUiState: GetOutingUUIDUiState,
    onBackClick: () -> Unit,
    onQrCreate: () -> Unit,
    onRemoteError: () -> Unit,
    onTimerFinish: () -> Unit
) {
    LaunchedEffect("qr create") {
        onQrCreate()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
    ) {
        GomsBackButton { onBackClick() }
        QrcodeGenerateText(Modifier)
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(120.dp))
            when (getOutingUUIDUiState) {
                GetOutingUUIDUiState.Loading -> {}
                is GetOutingUUIDUiState.Success -> {
                    val data = getOutingUUIDUiState.getOutingUUIDResponse

                    Image(painter = QrcodeGenerator(content = data.toString()), contentDescription = "outing qrcode image" )
                }
                is GetOutingUUIDUiState.Error -> onRemoteError
            }
            Spacer(modifier = Modifier.height(32.dp))
            QrcodeGenerateTimer(
                onTimerFinish = onTimerFinish
            )
        }
    }
}