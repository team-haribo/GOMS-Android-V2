package com.goms.login

import android.content.pm.ActivityInfo
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.goms.design_system.component.text.RowLinkText
import com.goms.design_system.component.textfield.GomsPasswordTextField
import com.goms.design_system.component.textfield.GomsTextField
import com.goms.design_system.theme.GomsTheme
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.theme.ThemeType
import com.goms.design_system.util.ThemeDevicePreviews
import com.goms.design_system.util.keyboardAsState
import com.goms.design_system.util.lockScreenOrientation
import com.goms.login.component.InputLoginText
import com.goms.login.viewmodel.LoginViewModel
import com.goms.login.viewmodel.uistate.LoginUiState
import com.goms.login.viewmodel.uistate.SaveTokenUiState
import com.goms.model.request.auth.LoginRequestModel
import com.goms.model.util.ResourceKeys

@Composable
internal fun InputLoginRoute(
    onBackClick: () -> Unit,
    onMainClick: () -> Unit,
    onRePasswordClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
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
        onRePasswordClick = onRePasswordClick,
        onErrorToast = onErrorToast,
        loginCallBack = {
            viewModel.login(
                body = LoginRequestModel(
                    "${viewModel.email.value}${ResourceKeys.EMAIL_DOMAIN}",
                    viewModel.password.value
                )
            )
        }
    )
}

@Composable
private fun InputLoginScreen(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    loginUiState: LoginUiState,
    saveTokenUiState: SaveTokenUiState,
    onBackClick: () -> Unit,
    onMainClick: () -> Unit,
    onRePasswordClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
    loginCallBack: () -> Unit
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

    DisposableEffect(loginUiState, saveTokenUiState) {
        when (loginUiState) {
            is LoginUiState.Loading -> Unit
            is LoginUiState.Success -> {
                when (saveTokenUiState) {
                    is SaveTokenUiState.Loading -> Unit
                    is SaveTokenUiState.Success -> onMainClick()
                    is SaveTokenUiState.Error -> {
                        isLoading = false
                        isError = true
                        onErrorToast(saveTokenUiState.exception, R.string.error_save_info)
                    }
                }
            }

            is LoginUiState.BadRequest -> {
                isLoading = false
                isError = true
                onErrorToast(null, R.string.error_password_mismatch)
            }

            is LoginUiState.NotFound -> {
                isLoading = false
                isError = true
                onErrorToast(null, R.string.error_user_missing)
            }

            is LoginUiState.EmailNotValid -> {
                isLoading = false
                isError = true
                onErrorToast(null, R.string.error_email_not_valid)
            }

            is LoginUiState.Error -> {
                isLoading = false
                isError = true
                onErrorToast(loginUiState.exception, R.string.error_login)
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
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            InputLoginText(modifier = Modifier.align(Alignment.Start))
            GomsSpacer(size = SpacerSize.MediumLarge)
            GomsTextField(
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                placeHolder = stringResource(id = R.string.email),
                setText = email,
                onValueChange = onEmailChange,
                isError = isError,
                singleLine = true
            )
            GomsSpacer(size = SpacerSize.Medium)
            GomsPasswordTextField(
                modifier = Modifier.fillMaxWidth(),
                isError = isError,
                isDescription = false,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                placeHolder = stringResource(id = R.string.password),
                setText = password,
                onValueChange = onPasswordChange,
                singleLine = true
            )
            GomsSpacer(size = SpacerSize.ExtraSmall)
            RowLinkText {
                onRePasswordClick()
            }
            Spacer(modifier = Modifier.weight(1f))
            GomsButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.login),
                state = if (email.isNotBlank() && password.isNotBlank()) ButtonState.Normal
                else ButtonState.Enable
            ) {
                loginCallBack()
                isLoading = true
            }
            GomsSpacer(height = animatedSpacerHeight)
        }
    }
    if (isLoading) {
        GomsCircularProgressIndicator()
    }
}

@ThemeDevicePreviews
@Composable
private fun InputLoginScreenPreview() {
    GomsTheme(ThemeType.SYSTEM.value) {
        InputLoginScreen(
            email = "GOMS",
            password = "GOMS",
            onEmailChange = {},
            onPasswordChange = {},
            loginUiState = LoginUiState.Loading,
            saveTokenUiState = SaveTokenUiState.Loading,
            onBackClick = {},
            onMainClick = {},
            onRePasswordClick = {},
            onErrorToast = { _, _ -> },
        ) {}
    }
}