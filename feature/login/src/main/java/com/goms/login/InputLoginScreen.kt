package com.goms.login

import android.content.pm.ActivityInfo
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.goms.common.result.Result
import com.goms.design_system.component.button.ButtonState
import com.goms.design_system.component.button.GomsBackButton
import com.goms.design_system.component.button.GomsButton
import com.goms.design_system.component.indicator.GomsCircularProgressIndicator
import com.goms.design_system.component.textfield.GomsTextField
import com.goms.design_system.theme.GomsTheme
import com.goms.design_system.util.keyboardAsState
import com.goms.design_system.util.lockScreenOrientation
import com.goms.login.component.InputLoginText
import com.goms.login.viewmodel.LoginViewModel
import com.goms.login.viewmodel.LoginUiState
import com.goms.model.request.auth.LoginRequest
import com.goms.ui.isStrongEmail

@Composable
fun InputLoginRoute(
    onBackClick: () -> Unit,
    onMainClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: String?) -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val loginUiState by viewModel.loginUiState.collectAsStateWithLifecycle()
    val saveTokenUiState by viewModel.saveTokenUiState.collectAsStateWithLifecycle()
    val email by viewModel.email.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()

    InputLoginScreen(
        email = email,
        password = password,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        loginUiState = loginUiState,
        saveTokenUiState = saveTokenUiState,
        onBackClick = onBackClick,
        onMainClick = onMainClick,
        onErrorToast = onErrorToast,
        loginCallBack = {
            viewModel.login(
                body = LoginRequest(
                    "${viewModel.email.value}@gsm.hs.kr",
                    viewModel.password.value
                )
            )
        }
    )
}

@Composable
fun InputLoginScreen(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    loginUiState: LoginUiState,
    saveTokenUiState: Result<Unit>,
    onBackClick: () -> Unit,
    onMainClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: String?) -> Unit,
    loginCallBack: () -> Unit
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

    DisposableEffect(loginUiState, saveTokenUiState) {
        when (loginUiState) {
            is LoginUiState.Loading -> Unit
            is LoginUiState.Success -> {
                when (saveTokenUiState) {
                    is Result.Loading -> Unit
                    is Result.Success -> onMainClick()
                    is Result.Error -> {
                        isLoading = false
                    }
                }
            }
            is LoginUiState.BadRequest -> {
                isLoading = false
                isError = true
                errorText = "비밀번호가 일치하지 않습니다"
                onErrorToast(null, "비밀번호가 일치하지 않습니다")
            }
            is LoginUiState.NotFound -> {
                isLoading = false
                isError = true
                errorText = "해당 유저가 존재하지 않습니다"
                onErrorToast(null, "해당 유저가 존재하지 않습니다")
            }
            is LoginUiState.Error -> {
                isLoading = false
                isError = true
                onErrorToast(loginUiState.exception, null)
            }
        }
        onDispose {}
    }

    lockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    GomsTheme { colors, typography ->
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
                InputLoginText(modifier = Modifier.align(Alignment.Start))
                Spacer(modifier = Modifier.weight(1.1f))
                GomsTextField(
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    placeHolder = "이메일",
                    setText = email,
                    onValueChange = onEmailChange,
                    isError = isError,
                    singleLine = true
                )
                GomsTextField(
                    modifier = Modifier.fillMaxWidth(),
                    isEmail = false,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    placeHolder = "비밀번호",
                    setText = password,
                    onValueChange = onPasswordChange,
                    isError = isError,
                    errorText = errorText,
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.weight(1f))
                GomsButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "로그인",
                    state = if (email.isNotBlank() && password.isNotBlank()) ButtonState.Normal
                    else ButtonState.Enable
                ) {
                    if (!isStrongEmail(email)) {
                        isLoading = false
                        onErrorToast(null, "이메일 형식이 올바르지 않습니다")
                    } else {
                        loginCallBack()
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
}