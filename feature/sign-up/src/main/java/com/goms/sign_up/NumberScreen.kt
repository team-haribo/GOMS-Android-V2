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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.goms.common.result.Result
import com.goms.design_system.component.button.ButtonState
import com.goms.design_system.component.button.GomsBackButton
import com.goms.design_system.component.button.GomsButton
import com.goms.design_system.component.textfield.NumberTextField
import com.goms.design_system.theme.GomsTheme
import com.goms.design_system.util.keyboardAsState
import com.goms.design_system.util.lockScreenOrientation
import com.goms.sign_up.component.NumberText
import com.goms.sign_up.viewmodel.SignUpViewModelProvider

@Composable
fun NumberRoute(
    viewModelStoreOwner: ViewModelStoreOwner,
    onBackClick: () -> Unit,
    onPasswordClick: () -> Unit,
) {
    SignUpViewModelProvider(viewModelStoreOwner = viewModelStoreOwner) { viewModel ->
        val verifyNumberResponse = viewModel.verifyNumberResponse.collectAsStateWithLifecycle()
        val number by viewModel.number.collectAsStateWithLifecycle()
        var isError by remember { mutableStateOf(false) }
        var errorText by remember { mutableStateOf("") }

        when (verifyNumberResponse.value) {
            is Result.Loading -> Unit
            is Result.Success -> {
                onPasswordClick()
                viewModel.initVerifyNumber()
            }
            is Result.Error -> {
                isError = true
                errorText = "잘못된 인증번호입니다"
            }
        }

        NumberScreen(
            number = number,
            isError = isError,
            errorText = errorText,
            onNumberChange = viewModel::onNumberChange,
            onBackClick = onBackClick,
            numberCallback = {
                viewModel.verifyNumber(
                    email = "${viewModel.email.value}@gsm.hs.kr",
                    authCode = viewModel.number.value
                )
            }
        )
    }
}

@Composable
fun NumberScreen(
    number: String,
    isError: Boolean,
    errorText: String,
    onNumberChange: (String) -> Unit,
    onBackClick: () -> Unit,
    numberCallback: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val isKeyboardOpen by keyboardAsState()
    var isHidden by remember { mutableStateOf(false) }
    val animatedSpacerHeight by animateDpAsState(targetValue = if (isHidden) 100.dp else 16.dp)

    LaunchedEffect(isKeyboardOpen) {
        if (isKeyboardOpen) {
            isHidden = false
        } else {
            isHidden = true
            focusManager.clearFocus()
        }
    }

    lockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED)
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
                NumberText(modifier = Modifier.align(Alignment.Start))
                Spacer(modifier = Modifier.weight(2.1f))
                NumberTextField(
                    text = number,
                    isError = isError,
                    errorText = errorText,
                    onValueChange = onNumberChange,
                    onResendClick = {}
                )
                Spacer(modifier = Modifier.weight(1f))
                GomsButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "인증",
                    state = if (number.isNotBlank()) ButtonState.Normal else ButtonState.Enable
                ) {
                    numberCallback()
                }
                Spacer(modifier = Modifier.height(animatedSpacerHeight))
            }
        }
    }
}