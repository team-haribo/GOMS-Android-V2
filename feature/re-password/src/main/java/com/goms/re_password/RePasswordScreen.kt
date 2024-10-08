package com.goms.re_password

import androidx.activity.ComponentActivity
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
import com.goms.design_system.component.dialog.GomsOneButtonDialog
import com.goms.design_system.component.indicator.GomsCircularProgressIndicator
import com.goms.design_system.component.spacer.GomsSpacer
import com.goms.design_system.component.spacer.SpacerSize
import com.goms.design_system.component.textfield.GomsPasswordTextField
import com.goms.design_system.theme.GomsTheme
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.theme.ThemeType
import com.goms.design_system.util.ThemeDevicePreviews
import com.goms.design_system.util.keyboardAsState
import com.goms.model.request.account.RePasswordRequestModel
import com.goms.re_password.component.RePasswordText
import com.goms.re_password.viewmodel.RePasswordViewmodel
import com.goms.re_password.viewmodel.uistate.RePasswordUiState

@Composable
internal fun RePasswordRoute(
    onBackClick: () -> Unit,
    onSuccessClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
    viewModel: RePasswordViewmodel = hiltViewModel(LocalContext.current as ComponentActivity),
) {
    val rePasswordUiState by viewModel.rePasswordUiState.collectAsStateWithLifecycle()
    val password by viewModel.newPassword.collectAsStateWithLifecycle()
    val passwordCheck by viewModel.newCheckPassword.collectAsStateWithLifecycle()

    RePasswordScreen(
        password = password,
        passwordCheck = passwordCheck,
        onPasswordChange = viewModel::onNewPasswordChange,
        onPasswordCheckChange = viewModel::onNewCheckPasswordChange,
        onSuccessClick = onSuccessClick,
        onBackClick = onBackClick,
        onErrorToast = onErrorToast,
        rePasswordUiState = rePasswordUiState,
        initRePassword = viewModel::initRePassword,
        rePasswordCallback = {
            viewModel.rePassword(
                body = RePasswordRequestModel(
                    password = viewModel.password.value,
                    newPassword = viewModel.newPassword.value
                )
            )
        }
    )
}

@Composable
private fun RePasswordScreen(
    password: String,
    passwordCheck: String,
    onPasswordChange: (String) -> Unit,
    onPasswordCheckChange: (String) -> Unit,
    onSuccessClick: () -> Unit,
    onBackClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
    rePasswordUiState: RePasswordUiState,
    initRePassword: () -> Unit,
    rePasswordCallback: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val isKeyboardOpen by keyboardAsState()
    val animatedSpacerHeight by animateDpAsState(targetValue = if (!isKeyboardOpen) 100.dp else 16.dp)
    var isLoading by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }
    var openDialog by remember { mutableStateOf(false) }

    LaunchedEffect(isKeyboardOpen) {
        if (!isKeyboardOpen) {
            focusManager.clearFocus()
        }
    }

    DisposableEffect(rePasswordUiState) {
        when (rePasswordUiState) {
            is RePasswordUiState.Loading -> Unit
            is RePasswordUiState.Success -> {
                openDialog = true
                isLoading = false
                isError = false
            }

            is RePasswordUiState.BadRequest -> {
                isLoading = false
                isError = true
                onErrorToast(null, R.string.error_password_not_match_or_already_use)
            }

            is RePasswordUiState.PasswordMismatch -> {
                isLoading = false
                isError = true
                onErrorToast(null, R.string.error_password_mismatch)
            }

            is RePasswordUiState.PasswordNotValid -> {
                isLoading = false
                isError = true
                onErrorToast(null, R.string.error_password_not_valid)
            }

            is RePasswordUiState.Error -> {
                isLoading = false
                isError = true
                onErrorToast(rePasswordUiState.exception, R.string.error_re_password)
            }
        }
        onDispose { initRePassword() }
    }

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
            GomsSpacer(size = SpacerSize.MediumLarge)
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
            GomsSpacer(size = SpacerSize.Medium)
            GomsPasswordTextField(
                modifier = Modifier.fillMaxWidth(),
                isDescription = true,
                isError = isError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                placeHolder = stringResource(id = R.string.check_password),
                setText = passwordCheck,
                onValueChange = onPasswordCheckChange,
                singleLine = true
            )
            Spacer(modifier = Modifier.weight(1f))
            GomsButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.completion),
                state = if (password.isNotBlank() && passwordCheck.isNotBlank()) ButtonState.Normal
                else ButtonState.Enable
            ) {
                rePasswordCallback()
                isLoading = true
            }
            GomsSpacer(height = animatedSpacerHeight)
        }
    }
    if (isLoading) {
        GomsCircularProgressIndicator()
    }
    if (openDialog) {
        GomsOneButtonDialog(
            openDialog = openDialog,
            onStateChange = {
                openDialog = it
            },
            title = stringResource(id = R.string.reset_password_completion),
            content = stringResource(id = R.string.go_back_home),
            buttonText = stringResource(id = R.string.check),
            onClick = onSuccessClick
        )
    }
}

@ThemeDevicePreviews
@Composable
private fun RePasswordScreenPreview() {
    GomsTheme(ThemeType.SYSTEM.value) {
        RePasswordScreen(
            password = "GOMS",
            passwordCheck = "GOMS",
            onPasswordChange = {},
            onPasswordCheckChange = {},
            onSuccessClick = {},
            onBackClick = {},
            onErrorToast = { _, _ -> },
            rePasswordUiState = RePasswordUiState.Loading,
            initRePassword = {},
        ) {}
    }
}