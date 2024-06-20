package com.goms.sign_up

import android.content.pm.ActivityInfo
import android.content.res.Configuration
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
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
import com.goms.design_system.component.textfield.NumberTextField
import com.goms.design_system.theme.GomsTheme
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.theme.ThemeType
import com.goms.design_system.util.keyboardAsState
import com.goms.design_system.util.lockScreenOrientation
import com.goms.model.request.auth.SendNumberRequestModel
import com.goms.model.util.ResourceKeys
import com.goms.sign_up.component.NumberText
import com.goms.sign_up.viewmodel.SignUpViewModel
import com.goms.sign_up.viewmodel.uistate.VerifyNumberUiState

@Composable
internal fun NumberRoute(
    onBackClick: () -> Unit,
    onPasswordClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
    viewModel: SignUpViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
) {
    val verifyNumberUiState by viewModel.verifyNumberUiState.collectAsStateWithLifecycle()
    val number by viewModel.number.collectAsStateWithLifecycle()

    NumberScreen(
        number = number,
        onNumberChange = viewModel::onNumberChange,
        verifyNumberUiState = verifyNumberUiState,
        onBackClick = onBackClick,
        onPasswordClick = onPasswordClick,
        numberCallback = {
            viewModel.verifyNumber(
                email = "${viewModel.email.value}${ResourceKeys.EMAIL_DOMAIN}",
                authCode = viewModel.number.value
            )
        },
        onErrorToast = onErrorToast,
        resentCallBack = { viewModel.sendNumber(body = SendNumberRequestModel("${viewModel.email.value}${ResourceKeys.EMAIL_DOMAIN}")) },
        initCallBack = { viewModel.initVerifyNumber() }
    )
}

@Composable
private fun NumberScreen(
    number: String,
    onNumberChange: (String) -> Unit,
    verifyNumberUiState: VerifyNumberUiState,
    onBackClick: () -> Unit,
    onPasswordClick: () -> Unit,
    numberCallback: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
    resentCallBack: () -> Unit,
    initCallBack: () -> Unit
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val isKeyboardOpen by keyboardAsState()
    val animatedSpacerHeight by animateDpAsState(targetValue = if (!isKeyboardOpen) 100.dp else 16.dp)
    var isLoading by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }
    var errorText by remember { mutableStateOf("") }
    var openDialog by remember { mutableStateOf(false) }

    LaunchedEffect(isKeyboardOpen) {
        if (!isKeyboardOpen) {
            focusManager.clearFocus()
        }
    }

    DisposableEffect(verifyNumberUiState) {
        when (verifyNumberUiState) {
            is VerifyNumberUiState.Loading -> Unit
            is VerifyNumberUiState.Success -> onPasswordClick()
            is VerifyNumberUiState.BadRequest -> {
                isLoading = false
                isError = true
                errorText = context.getString(R.string.error_number_mismatch)
                onErrorToast(null, R.string.error_number_mismatch)
            }

            is VerifyNumberUiState.NotFound -> {
                isLoading = false
                isError = true
                errorText = context.getString(R.string.error_number_not_valid)
                onErrorToast(null, R.string.error_number_not_valid)
            }

            is VerifyNumberUiState.Error -> {
                isLoading = false
                isError = true
                errorText = context.getString(R.string.error_verify_number)
                onErrorToast(verifyNumberUiState.exception, R.string.error_verify_number)
            }
        }
        onDispose { initCallBack() }
    }

    lockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED)
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
            NumberText(modifier = Modifier.align(Alignment.Start))
            GomsSpacer(size = SpacerSize.MediumLarge)
            NumberTextField(
                setText = number,
                isError = isError,
                placeHolder = stringResource(id = R.string.input_number),
                errorText = errorText,
                onValueChange = onNumberChange,
                onResendClick = {
                    resentCallBack()
                    openDialog = true
                }
            )
            Spacer(modifier = Modifier.weight(1f))
            GomsButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.certified),
                state = if (number.isNotBlank()) ButtonState.Normal else ButtonState.Enable
            ) {
                numberCallback()
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
            title = stringResource(id = R.string.resend_completion),
            content = stringResource(id = R.string.resend_completion_description),
            buttonText = stringResource(id = R.string.check),
            onClick = {}
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun NumberScreenPreview() {
    GomsTheme(ThemeType.SYSTEM.value) {
        NumberScreen(
            number = "GOMS",
            onNumberChange = {},
            verifyNumberUiState = VerifyNumberUiState.Loading,
            onBackClick = {},
            onPasswordClick = {},
            numberCallback = {},
            onErrorToast = { _, _ -> },
            resentCallBack = {}
        ) {}
    }
}