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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import com.goms.design_system.component.button.ButtonState
import com.goms.design_system.component.button.GomsButton
import com.goms.design_system.component.text.LinkText
import com.goms.design_system.component.textfield.GomsTextField
import com.goms.design_system.icon.GomsIcon
import com.goms.design_system.theme.GomsTheme
import com.goms.design_system.util.keyboardAsState
import com.goms.design_system.util.lockScreenOrientation
import com.goms.login.component.NumberLoginText

@Composable
fun EmailLoginRoute(
    onLoginClick: () -> Unit,
    onNumberLoginClick: () -> Unit
) {
    EmailLoginScreen(
        onLoginClick = onLoginClick,
        onNumberLoginClick = onNumberLoginClick
    )
}

@Composable
fun EmailLoginScreen(
    onLoginClick: () -> Unit,
    onNumberLoginClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val isKeyboardOpen by keyboardAsState()
    var isHidden by remember { mutableStateOf(false) }
    val animatedSpacerHeight by animateDpAsState(targetValue = if (isHidden) 16.dp else 160.dp)

    var email by remember { mutableStateOf("") }
    var isEmailError by remember { mutableStateOf(false) }
    var errorText by remember { mutableStateOf("") }

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
                .pointerInput(Unit) {
                    detectTapGestures {
                        focusManager.clearFocus()
                    }
                }
                .background(colors.BLACK)
                .padding(start = 20.dp, end = 20.dp, top = 50.dp)
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))
            GomsIcon()
            Spacer(modifier = Modifier.height(48.dp))
            NumberLoginText()
            Spacer(modifier = Modifier.height(40.dp))
            GomsTextField(
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                placeHolder = "이메일",
                setText = email,
                isError = isEmailError,
                errorText = errorText,
                onValueChange = { emailChange ->
                    email = emailChange
                },
                singleLine = true
            )
            Spacer(modifier = Modifier.height(58.5.dp))
            GomsButton(
                modifier = Modifier.fillMaxWidth(),
                text = "인증번호 받기",
                state = if (email.isNotBlank()) ButtonState.Normal else ButtonState.Enable
            ) {
                onNumberLoginClick()
            }
            Spacer(modifier = Modifier.height(animatedSpacerHeight))
            LinkText(text = "GAuth로 로그인하기") {
                onLoginClick()
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}