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
import com.goms.design_system.component.button.ButtonState
import com.goms.design_system.component.button.GomsBackButton
import com.goms.design_system.component.button.GomsButton
import com.goms.design_system.component.textfield.GomsTextField
import com.goms.design_system.theme.GomsTheme
import com.goms.design_system.util.keyboardAsState
import com.goms.design_system.util.lockScreenOrientation
import com.goms.login.component.InputLoginText
import com.goms.login.viewmodel.AuthViewModel
import com.goms.login.viewmodel.LoginUiState
import com.goms.model.request.auth.LoginRequest

@Composable
fun InputLoginRoute(
    onBackClick: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel(),
) {
    val loginResponse = viewModel.loginResponse.collectAsStateWithLifecycle()
    var isError by remember { mutableStateOf(false) }
    var errorText by remember { mutableStateOf("") }

    when (val state = loginResponse.value) {
        is LoginUiState.Loading -> Unit
        is LoginUiState.Success -> {
            viewModel.saveToken(token = state.loginResponse)
        }
        is LoginUiState.Error -> {
            isError = true
            errorText = "로그인에 실패하였습니다."
        }
    }

    InputLoginScreen(
        onBackClick = onBackClick,
        isError = isError,
        errorText = errorText,
        loginCallBack = { email, password ->
            viewModel.login(body = LoginRequest(email, password))
        }
    )
}


@Composable
fun InputLoginScreen(
    onBackClick: () -> Unit,
    isError: Boolean,
    errorText: String,
    loginCallBack: (email: String, password: String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val isKeyboardOpen by keyboardAsState()
    var isHidden by remember { mutableStateOf(false) }
    val animatedSpacerHeight by animateDpAsState(targetValue = if (isHidden) 100.dp else 16.dp)

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(isKeyboardOpen) {
        if (isKeyboardOpen) {
            isHidden = false
        } else {
            isHidden = true
            focusManager.clearFocus()
        }
    }

    lockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
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
                InputLoginText(modifier = Modifier.align(Alignment.Start))
                Spacer(modifier = Modifier.weight(1.1f))
                GomsTextField(
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    placeHolder = "이메일",
                    setText = email,
                    onValueChange = { emailChange ->
                        email = emailChange
                    },
                    isError = isError,
                    singleLine = true
                )
                GomsTextField(
                    modifier = Modifier.fillMaxWidth(),
                    isEmail = false,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    placeHolder = "비밀번호",
                    setText = password,
                    onValueChange = { passwordChange ->
                        password = passwordChange
                    },
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
                    loginCallBack("$email@gsm.hs.kr", password)
                }
                Spacer(modifier = Modifier.height(animatedSpacerHeight))
            }
        }
    }
}