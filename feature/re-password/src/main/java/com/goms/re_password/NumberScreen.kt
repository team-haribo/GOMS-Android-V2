package com.goms.re_password

import android.content.pm.ActivityInfo
import androidx.activity.ComponentActivity
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.goms.common.result.Result
import com.goms.design_system.component.button.ButtonState
import com.goms.design_system.component.button.GomsBackButton
import com.goms.design_system.component.button.GomsButton
import com.goms.design_system.component.indicator.GomsCircularProgressIndicator
import com.goms.design_system.component.textfield.NumberTextField
import com.goms.design_system.theme.GomsTheme
import com.goms.design_system.util.keyboardAsState
import com.goms.design_system.util.lockScreenOrientation
import com.goms.model.request.auth.SendNumberRequest
import com.goms.re_password.component.NumberText
import com.goms.re_password.viewmodel.RePasswordViewmodel
import com.goms.re_password.viewmodel.VerifyNumberUiState

@Composable
fun NumberRoute(
    onBackClick: () -> Unit,
    onRePasswordClick: () -> Unit,
    viewModel: RePasswordViewmodel = hiltViewModel(LocalContext.current as ComponentActivity)
) {
    val verifyNumberUiState by viewModel.verifyNumberUiState.collectAsState()
    val number by viewModel.number.collectAsStateWithLifecycle()

    NumberScreen(
        number = number,
        onNumberChange = viewModel::onNumberChange,
        verifyNumberUiState = verifyNumberUiState,
        onBackClick = onBackClick,
        onRePasswordClick = onRePasswordClick,
        numberCallback = {
            viewModel.verifyNumber(
                email = "${viewModel.email.value}@gsm.hs.kr",
                authCode = viewModel.number.value
            )
        },
        resentCallBack = { viewModel.sendNumber(body = SendNumberRequest("${viewModel.email.value}@gsm.hs.kr")) },
        initCallBack = { viewModel.initVerifyNumber() }
    )
}

@Composable
fun NumberScreen(
    number: String,
    onNumberChange: (String) -> Unit,
    verifyNumberUiState: VerifyNumberUiState,
    onBackClick: () -> Unit,
    onRePasswordClick: () -> Unit,
    numberCallback: () -> Unit,
    resentCallBack: () -> Unit,
    initCallBack: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val isKeyboardOpen by keyboardAsState()
    val animatedSpacerHeight by animateDpAsState(targetValue = if (!isKeyboardOpen) 100.dp else 16.dp)
    var isLoading by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }
    var errorText by remember { mutableStateOf("") }

    LaunchedEffect(isKeyboardOpen) {
        if (!isKeyboardOpen) {
            focusManager.clearFocus()
        }
    }

    DisposableEffect(verifyNumberUiState) {
        when (verifyNumberUiState) {
            is VerifyNumberUiState.Loading -> Unit
            is VerifyNumberUiState.Success -> onRePasswordClick()
            is VerifyNumberUiState.BadRequest -> {
                isLoading = false
                isError = true
                errorText = "인증번호가 일치하지 않습니다"
            }
            is VerifyNumberUiState.NotFound -> {
                isLoading = false
                isError = true
                errorText = "잘못된 인증번호입니다"
            }
            is VerifyNumberUiState.Error -> {
                isLoading = false
                isError = true
            }
        }
        onDispose { initCallBack() }
    }

    lockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED)
    GomsTheme { colors, typography ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.BLACK)
                .statusBarsPadding()
                .navigationBarsPadding()
                .imePadding()
                .pointerInput(Unit) {
                    detectTapGestures {
                        focusManager.clearFocus()
                    }
                }
        ) {
            GomsBackButton {
                onBackClick()
            }
            Column(
                modifier = Modifier.padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                NumberText(modifier = Modifier.align(Alignment.Start))
                Spacer(modifier = Modifier.weight(2.1f))
                NumberTextField(
                    text = number,
                    isError = isError,
                    errorText = errorText,
                    onValueChange = onNumberChange,
                    onResendClick = {
                        resentCallBack()
                    }
                )
                Spacer(modifier = Modifier.weight(1f))
                GomsButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "인증",
                    state = if (number.isNotBlank()) ButtonState.Normal else ButtonState.Enable
                ) {
                    numberCallback()
                    isLoading = true
                }
                Spacer(modifier = Modifier.height(animatedSpacerHeight))
            }
        }
        if (isLoading) {
            GomsCircularProgressIndicator()
        }
    }
}