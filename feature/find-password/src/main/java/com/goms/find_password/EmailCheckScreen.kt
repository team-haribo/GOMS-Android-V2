package com.goms.find_password

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.goms.design_system.component.bottomsheet.BottomSheetHeader
import com.goms.design_system.component.button.ButtonState
import com.goms.design_system.component.button.GomsBackButton
import com.goms.design_system.component.button.GomsButton
import com.goms.design_system.component.indicator.GomsCircularProgressIndicator
import com.goms.design_system.component.spacer.GomsSpacer
import com.goms.design_system.component.spacer.SpacerSize
import com.goms.design_system.component.textfield.GomsTextField
import com.goms.design_system.theme.GomsTheme
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.theme.ThemeType
import com.goms.design_system.util.keyboardAsState
import com.goms.design_system.util.lockScreenOrientation
import com.goms.find_password.component.FindPasswordText
import com.goms.model.request.auth.SendNumberRequestModel
import com.goms.find_password.viewmodel.FindPasswordViewmodel
import com.goms.find_password.viewmodel.uistate.SendNumberUiState
import com.goms.model.util.ResourceKeys

@Composable
internal fun EmailCheckRoute(
    onBackClick: () -> Unit,
    onNumberClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
    viewModel: FindPasswordViewmodel = hiltViewModel(LocalContext.current as ComponentActivity)
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
                body = SendNumberRequestModel("${viewModel.email.value}${ResourceKeys.EMAIL_DOMAIN}")
            )
        },
        onNumberClick = onNumberClick,
        initCallBack = { viewModel.initSendNumber() },
        onErrorToast = onErrorToast
    )
}

@Composable
private fun EmailCheckScreen(
    email: String,
    onEmailChange: (String) -> Unit,
    onBackClick: () -> Unit,
    onNumberClick: () -> Unit,
    emailCheckCallBack: () -> Unit,
    initCallBack: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
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
            is SendNumberUiState.EmailNotValid -> {
                isLoading = false
                onErrorToast(null, R.string.error_email_not_valid)
            }

            is SendNumberUiState.TooManyRequest -> {
                isLoading = false
                onErrorToast(null, R.string.error_too_many_request_send_email)
            }

            is SendNumberUiState.Error -> {
                isLoading = false
                onErrorToast(sendNumberUiState.exception, R.string.error_send_number)
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
            FindPasswordText(modifier = Modifier.align(Alignment.Start))
            GomsSpacer(size = SpacerSize.MediumLarge)
            GomsTextField(
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                placeHolder = stringResource(id = R.string.email),
                setText = email,
                onValueChange = onEmailChange,
                singleLine = true
            )
            Spacer(modifier = Modifier.weight(1f))
            GomsButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.get_number),
                state = if (email.isNotBlank()) ButtonState.Normal
                else ButtonState.Enable
            ) {
                emailCheckCallBack()
                isLoading = true
            }
            GomsSpacer(height = animatedSpacerHeight)
        }
    }
    if (isLoading) {
        GomsCircularProgressIndicator()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun EmailCheckScreenPreview() {
    GomsTheme(ThemeType.SYSTEM.value) {
        EmailCheckScreen(
            email = "GOMS",
            onEmailChange = {},
            onBackClick = {},
            onNumberClick = {},
            emailCheckCallBack = {},
            initCallBack = {},
            onErrorToast = { _, _ -> },
            sendNumberUiState = SendNumberUiState.Loading
        )
    }
}