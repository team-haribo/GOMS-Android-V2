package com.goms.sign_up

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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.goms.common.result.Result
import com.goms.design_system.component.button.ButtonState
import com.goms.design_system.component.button.GomsBackButton
import com.goms.design_system.component.button.GomsButton
import com.goms.design_system.component.indicator.GomsCircularProgressIndicator
import com.goms.design_system.component.textfield.GomsPasswordTextField
import com.goms.design_system.theme.GomsTheme
import com.goms.design_system.util.keyboardAsState
import com.goms.design_system.util.lockScreenOrientation
import com.goms.model.enum.Gender
import com.goms.model.enum.Major
import com.goms.model.request.auth.SignUpRequest
import com.goms.sign_up.component.PasswordText
import com.goms.sign_up.viewmodel.SignUpViewModelProvider
import com.goms.ui.isStrongPassword

@Composable
fun PasswordRoute(
    viewModelStoreOwner: ViewModelStoreOwner,
    onBackClick: () -> Unit,
    onLoginClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: String?) -> Unit
) {
    SignUpViewModelProvider(viewModelStoreOwner = viewModelStoreOwner) { viewModel ->
        val signUpUiState by viewModel.signUpUiState.collectAsStateWithLifecycle()
        val password by viewModel.password.collectAsStateWithLifecycle()
        val checkPassword by viewModel.checkPassword.collectAsStateWithLifecycle()

        PasswordScreen(
            password = password,
            checkPassword = checkPassword,
            onPasswordChange = viewModel::onPasswordChange,
            onCheckPasswordChange = viewModel::onCheckPasswordChange,
            signUpUiState = signUpUiState,
            onBackClick = onBackClick,
            onLoginClick = onLoginClick,
            onErrorToast = onErrorToast,
            passwordCallback = {
                viewModel.signUp(
                    body = SignUpRequest(
                        email = "${viewModel.email.value}@gsm.hs.kr",
                        password = viewModel.password.value,
                        name = viewModel.name.value,
                        gender = Gender.values().find { it.value == viewModel.gender.value }!!.name,
                        major = Major.values().find { it.value == viewModel.major.value }!!.name
                    )
                )
            }
        )
    }
}

@Composable
fun PasswordScreen(
    password: String,
    checkPassword: String,
    onPasswordChange: (String) -> Unit,
    onCheckPasswordChange: (String) -> Unit,
    signUpUiState: Result<Unit>,
    onBackClick: () -> Unit,
    onLoginClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: String?) -> Unit,
    passwordCallback: () -> Unit
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

    DisposableEffect(signUpUiState) {
        when (signUpUiState) {
            is Result.Loading -> Unit
            is Result.Success -> onLoginClick()
            is Result.Error -> {
                isLoading = false
                isError = true
                onErrorToast(signUpUiState.exception, null)
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
                PasswordText(modifier = Modifier.align(Alignment.Start))
                Spacer(modifier = Modifier.weight(1.1f))
                GomsPasswordTextField(
                    modifier = Modifier.fillMaxWidth(),
                    isError = isError,
                    isDescription = false,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    placeHolder = "비밀번호",
                    setText = password,
                    onValueChange = onPasswordChange,
                    singleLine = true
                )
                GomsPasswordTextField(
                    modifier = Modifier.fillMaxWidth(),
                    isDescription = true,
                    isError = isError,
                    errorText = errorText,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    placeHolder = "비밀번호 확인",
                    setText = checkPassword,
                    onValueChange = onCheckPasswordChange,
                    singleLine = true
                )
                Spacer(modifier = Modifier.weight(1f))
                GomsButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "회원가입",
                    state = if (password.isNotBlank() && checkPassword.isNotBlank()) ButtonState.Normal else ButtonState.Enable
                ) {
                    if (password != checkPassword) {
                        isError = true
                        errorText = "비밀번호가 일치하지 않습니다"
                        onErrorToast(null, "비밀번호가 일치하지 않습니다")
                    } else if (!isStrongPassword(password)) {
                        isError = true
                        errorText = "비밀번호 요구사항을 충족하지 않습니다"
                        onErrorToast(null, "비밀번호 요구사항을 충족하지 않습니다")
                    } else {
                        passwordCallback()
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