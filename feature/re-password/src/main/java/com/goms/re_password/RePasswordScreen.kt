package com.goms.re_password

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.goms.design_system.component.button.ButtonState
import com.goms.design_system.component.button.GomsBackButton
import com.goms.design_system.component.button.GomsButton
import com.goms.design_system.component.textfield.GomsPasswordTextField
import com.goms.design_system.theme.GomsTheme
import com.goms.design_system.util.keyboardAsState
import com.goms.re_password.component.RePasswordText
import com.goms.re_password.viewmodel.RePasswordViewmodelProvider

@Composable
fun RePasswordRoute(
    viewModelStoreOwner: ViewModelStoreOwner,
    onBackClick: () -> Unit,
    onSuccessClick: () -> Unit,
) {
    RePasswordViewmodelProvider(viewModelStoreOwner = viewModelStoreOwner) { viewModel ->
        val password by viewModel.password.collectAsStateWithLifecycle()
        val checkPassword by viewModel.email.collectAsStateWithLifecycle()

        RePasswordScreen(
            password = password,
            passwordCheck = checkPassword,
            onPasswordChange = viewModel::onPasswordChange,
            onPasswordCheckChange = viewModel::onCheckPasswordChange,
            onSuccessClick = onSuccessClick,
            onBackClick = onBackClick,
        )
    }
}

@Composable
fun RePasswordScreen(
    password: String,
    passwordCheck: String,
    onPasswordChange: (String) -> Unit,
    onPasswordCheckChange: (String) -> Unit,
    onSuccessClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val isKeyboardOpen by keyboardAsState()

    LaunchedEffect(isKeyboardOpen) {
        if (!isKeyboardOpen) {
            focusManager.clearFocus()
        }
    }

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
                RePasswordText(modifier = Modifier.align(Alignment.Start))
                Spacer(modifier = Modifier.weight(1.1f))
                GomsPasswordTextField(
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    placeHolder = "비밀번호",
                    setText = password,
                    onValueChange = onPasswordChange,
                    singleLine = true
                )
                GomsPasswordTextField(
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    placeHolder = "비밀번호 확인",
                    setText = passwordCheck,
                    onValueChange = onPasswordCheckChange,
                    singleLine = true,
                    isDescription = true
                )
                Spacer(modifier = Modifier.weight(1f))
                GomsButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "완료",
                    state = if (password.isNotBlank() && passwordCheck.isNotBlank()) ButtonState.Normal
                    else ButtonState.Enable
                ) {
                    onSuccessClick()
                }
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}