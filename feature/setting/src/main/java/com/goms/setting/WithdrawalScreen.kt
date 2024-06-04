package com.goms.setting

import com.goms.setting.component.WithdrawalText
import com.goms.setting.viewmodel.SettingViewModel
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
import com.goms.design_system.component.textfield.GomsPasswordTextField
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.util.keyboardAsState
import com.goms.design_system.util.lockScreenOrientation
import com.goms.setting.viewmodel.uistate.WithdrawalUiState

@Composable
internal fun WithdrawalRoute(
    onBackClick: () -> Unit,
    onWithdrawal: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
    viewModel: SettingViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
) {
    val password by viewModel.password.collectAsStateWithLifecycle()
    val withdrawalUiState by viewModel.withdrawalUiState.collectAsStateWithLifecycle()

    WithdrawalScreen(
        password = password,
        withdrawalUiState = withdrawalUiState,
        onPasswordChange = viewModel::onPasswordChange,
        onBackClick = onBackClick,
        onWithdrawalClick = viewModel::withdrawal,
        onErrorToast = onErrorToast,
        onWithdrawal = onWithdrawal
    )
}

@Composable
private fun WithdrawalScreen(
    password: String,
    withdrawalUiState: WithdrawalUiState,
    onPasswordChange: (String) -> Unit,
    onBackClick: () -> Unit,
    onWithdrawalClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
    onWithdrawal: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val isKeyboardOpen by keyboardAsState()
    val animatedSpacerHeight by animateDpAsState(targetValue = if (!isKeyboardOpen) 100.dp else 16.dp)
    var isLoading by remember { mutableStateOf(false) }
    var openDialog by remember { mutableStateOf(false) }

    LaunchedEffect(isKeyboardOpen) {
        if (!isKeyboardOpen) {
            focusManager.clearFocus()
        }
    }

    when(withdrawalUiState) {
        is WithdrawalUiState.Loading -> Unit
        is WithdrawalUiState.Success -> {
            openDialog = true
            isLoading = false
        }
        is WithdrawalUiState.BadRequest -> {
            isLoading = false
            onErrorToast(null, R.string.error_password_not_match_or_already_use)
        }
        is WithdrawalUiState.Error -> {
            isLoading = false
            onErrorToast(null,R.string.withdrawal)
        }
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
            WithdrawalText(modifier = Modifier.align(Alignment.Start))
            Spacer(modifier = Modifier.height(28.dp))
            GomsPasswordTextField(
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                placeHolder = stringResource(id = R.string.now_password),
                setText = password,
                onValueChange = onPasswordChange,
                singleLine = true
            )
            Spacer(modifier = Modifier.weight(1f))
            GomsButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.next),
                state = if (password.isNotBlank()) ButtonState.Normal
                else ButtonState.Enable
            ) {
                onWithdrawalClick()
                isLoading = true
            }
            Spacer(modifier = Modifier.height(animatedSpacerHeight))
        }
    }

    if (isLoading) {
        GomsCircularProgressIndicator()
    }
}