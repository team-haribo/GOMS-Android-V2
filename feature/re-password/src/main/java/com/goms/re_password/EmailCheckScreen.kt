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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.goms.design_system.component.button.ButtonState
import com.goms.design_system.component.button.GomsBackButton
import com.goms.design_system.component.button.GomsButton
import com.goms.design_system.component.indicator.GomsCircularProgressIndicator
import com.goms.design_system.component.textfield.GomsTextField
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.util.keyboardAsState
import com.goms.design_system.util.lockScreenOrientation
import com.goms.model.request.auth.SendNumberRequestModel
import com.goms.re_password.component.RePasswordText
import com.goms.re_password.viewmodel.RePasswordViewmodel
import com.goms.re_password.viewmodel.SendNumberUiState
import com.goms.ui.isStrongEmail

@Composable
fun EmailCheckRoute(
    onBackClick: () -> Unit,
    onNumberClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: String?) -> Unit,
    viewModel: RePasswordViewmodel = hiltViewModel(LocalContext.current as ComponentActivity)
) {
    val sendNumberUiState by viewModel.sendNumberUiState.collectAsStateWithLifecycle()
    val email by viewModel.email.collectAsStateWithLifecycle()

    EmailCheckScreen(
        email = email,
        onEmailChange = viewModel::onEmailChange,
        onBackClick = onBackClick,
        sendNumberUiState = sendNumberUiState,
        emailCheckCallBack = {
            viewModel.sendNumber(
                body = SendNumberRequestModel("${viewModel.email.value}@gsm.hs.kr")
        ) },
        onNumberClick = onNumberClick,
        initCallBack = { viewModel.initSendNumber() },
        onErrorToast = onErrorToast
    )
}

@Composable
fun EmailCheckScreen(
    email: String,
    onEmailChange: (String) -> Unit,
    onBackClick: () -> Unit,
    onNumberClick: () -> Unit,
    emailCheckCallBack: () -> Unit,
    initCallBack: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: String?) -> Unit,
    sendNumberUiState: SendNumberUiState
) {
    val focusManager = LocalFocusManager.current
    val isKeyboardOpen by keyboardAsState()
    var isLoading by remember { mutableStateOf(false) }
    val animatedSpacerHeight by animateDpAsState(targetValue = if (!isKeyboardOpen) 100.dp else 16.dp)

    LaunchedEffect(isKeyboardOpen) {
        if (!isKeyboardOpen) {
            focusManager.clearFocus()
        }
    }

    DisposableEffect(sendNumberUiState) {
        when (sendNumberUiState) {
            is SendNumberUiState.Loading -> Unit
            is SendNumberUiState.Success -> onNumberClick()
            is SendNumberUiState.Error -> {
                isLoading = false
                onErrorToast(sendNumberUiState.exception, "인증번호 전송이 실패했습니다.")
            }
        }
        onDispose { initCallBack() }
    }

    lockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.BACKGROUND)
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
            RePasswordText(modifier = Modifier.align(Alignment.Start))
            Spacer(modifier = Modifier.height(28.dp))
            GomsTextField(
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                placeHolder = "이메일",
                setText = email,
                onValueChange = onEmailChange,
                singleLine = true
            )
            Spacer(modifier = Modifier.weight(1f))
            GomsButton(
                modifier = Modifier.fillMaxWidth(),
                text = "인증번호 받기",
                state = if (email.isNotBlank()) ButtonState.Normal
                else ButtonState.Enable
            ) {
                if (!isStrongEmail(email)) {
                    isLoading = false
                    onErrorToast(null, "이메일 형식이 올바르지 않습니다")
                } else {
                    emailCheckCallBack()
                    isLoading = true
                }
            }
            Spacer(modifier = Modifier.height(animatedSpacerHeight))
        }
    }
    if (isLoading) {
        GomsCircularProgressIndicator()
    }
}