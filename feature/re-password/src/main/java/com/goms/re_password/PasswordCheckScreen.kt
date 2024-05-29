package com.goms.re_password

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import com.goms.design_system.component.textfield.GomsPasswordTextField
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.util.keyboardAsState
import com.goms.design_system.util.lockScreenOrientation
import com.goms.re_password.component.RePasswordText
import com.goms.re_password.viewmodel.RePasswordViewmodel

@Composable
internal fun PasswordCheckRoute(
    onBackClick: () -> Unit,
    onRePasswordClick: () -> Unit,
    viewModel: RePasswordViewmodel = hiltViewModel(LocalContext.current as ComponentActivity)
) {
    val password by viewModel.password.collectAsStateWithLifecycle()

    PasswordCheckScreen(
        password = password,
        onPasswordChange = viewModel::onPasswordChange,
        onBackClick = onBackClick,
        onRePasswordClick = onRePasswordClick
    )
}

@Composable
private fun PasswordCheckScreen(
    password: String,
    onPasswordChange: (String) -> Unit,
    onBackClick: () -> Unit,
    onRePasswordClick: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val isKeyboardOpen by keyboardAsState()
    val animatedSpacerHeight by animateDpAsState(targetValue = if (!isKeyboardOpen) 100.dp else 16.dp)

    LaunchedEffect(isKeyboardOpen) {
        if (!isKeyboardOpen) {
            focusManager.clearFocus()
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
            RePasswordText(modifier = Modifier.align(Alignment.Start))
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
                onRePasswordClick()
            }
            Spacer(modifier = Modifier.height(animatedSpacerHeight))
        }
    }
}