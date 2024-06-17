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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.goms.design_system.component.button.ButtonState
import com.goms.design_system.component.button.GomsBackButton
import com.goms.design_system.component.button.GomsButton
import com.goms.design_system.component.indicator.GomsCircularProgressIndicator
import com.goms.design_system.component.spacer.GomsSpacer
import com.goms.design_system.component.spacer.SpacerSize
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

@Composable
internal fun PasswordRoute(
    onBackClick: () -> Unit,
    onLoginClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
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
        },
        initCallBack = { viewModel.initSignUp() }
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
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
    passwordCallback: () -> Unit,
    initCallBack: () -> Unit
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
                onErrorToast(null, R.string.error_already_exists_account)
            }

            is SignUpUiState.PasswordMismatch -> {
                isLoading = false
                isError = true
                onErrorToast(null, R.string.error_password_mismatch)
            }

            is SignUpUiState.PasswordNotValid -> {
                isLoading = false
                isError = true
                onErrorToast(null, R.string.error_password_not_valid)
            }

            is SignUpUiState.Error -> {
                isLoading = false
                isError = true
                onErrorToast(signUpUiState.exception, R.string.error_sign_up)
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
            PasswordText(modifier = Modifier.align(Alignment.Start))
            GomsSpacer(size = SpacerSize.MediumLarge)
            GomsPasswordTextField(
                modifier = Modifier.fillMaxWidth(),
                isError = isError,
                isDescription = false,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                placeHolder = stringResource(id = R.string.password),
                setText = password,
                onValueChange = onPasswordChange,
                singleLine = true
            )
            GomsSpacer(size = SpacerSize.Medium)
            GomsPasswordTextField(
                modifier = Modifier.fillMaxWidth(),
                isDescription = true,
                isError = isError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                placeHolder = stringResource(id = R.string.check_password),
                setText = checkPassword,
                onValueChange = onCheckPasswordChange,
                singleLine = true
            )
            Spacer(modifier = Modifier.weight(1f))
            GomsButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.sign_up),
                state = if (password.isNotBlank() && checkPassword.isNotBlank()) ButtonState.Normal else ButtonState.Enable
            ) {
                passwordCallback()
                isLoading = true
            }
            GomsSpacer(height = animatedSpacerHeight)
        }
    }
    if (isLoading) {
        GomsCircularProgressIndicator()
    }
}