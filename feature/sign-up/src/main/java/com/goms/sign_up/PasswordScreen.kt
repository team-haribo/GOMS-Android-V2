package com.goms.sign_up

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
import com.goms.design_system.component.textfield.GomsPasswordTextField
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.util.keyboardAsState
import com.goms.design_system.util.lockScreenOrientation
import com.goms.model.enum.Gender
import com.goms.model.enum.Major
import com.goms.model.request.auth.SignUpRequestModel
import com.goms.model.util.ResourceKeys
import com.goms.sign_up.component.PasswordText
import com.goms.sign_up.viewmodel.uistate.SignUpUiState
import com.goms.sign_up.viewmodel.SignUpViewModel
import com.goms.ui.isValidPassword

@Composable
internal fun PasswordRoute(
    onBackClick: () -> Unit,
    onLoginClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: String?) -> Unit,
    viewModel: SignUpViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
) {
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
                body = SignUpRequestModel(
                    email = "${viewModel.email.value}${ResourceKeys.EMAIL_DOMAIN}",
                    password = viewModel.password.value,
                    name = viewModel.name.value,
                    gender = Gender.values().find { it.value == viewModel.gender.value }!!.name,
                    major = Major.values().find { it.fullName == viewModel.major.value }!!.name
                )
            )
        }
    )
}

@Composable
private fun PasswordScreen(
    password: String,
    checkPassword: String,
    onPasswordChange: (String) -> Unit,
    onCheckPasswordChange: (String) -> Unit,
    signUpUiState: SignUpUiState,
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

    LaunchedEffect(isKeyboardOpen) {
        if (!isKeyboardOpen) {
            focusManager.clearFocus()
        }
    }

    DisposableEffect(signUpUiState) {
        when (signUpUiState) {
            is SignUpUiState.Loading -> Unit
            is SignUpUiState.Success -> onLoginClick()
            is SignUpUiState.Conflict -> {
                isLoading = false
                isError = true
                onErrorToast(null, "이미 존재하는 계정입니다")
            }

            is SignUpUiState.PasswordMismatch -> {
                isLoading = false
                isError = true
                onErrorToast(null, "비밀번호가 일치하지 않습니다")
            }

            is SignUpUiState.PasswordNotValid -> {
                isLoading = false
                isError = true
                onErrorToast(null, "비밀번호 요구사항을 충족하지 않습니다")
            }

            is SignUpUiState.Error -> {
                isLoading = false
                isError = true
                onErrorToast(signUpUiState.exception, null)
            }
        }
        onDispose {}
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
            PasswordText(modifier = Modifier.align(Alignment.Start))
            Spacer(modifier = Modifier.height(28.dp))
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
                passwordCallback()
                isLoading = true
            }
            Spacer(modifier = Modifier.height(animatedSpacerHeight))
        }
    }
    if (isLoading) {
        GomsCircularProgressIndicator()
    }
}