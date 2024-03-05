package com.goms.login

import android.content.pm.ActivityInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.goms.design_system.component.button.ButtonState
import com.goms.design_system.component.button.GomsButton
import com.goms.design_system.component.text.LinkText
import com.goms.design_system.icon.GomsIcon
import com.goms.design_system.theme.GomsTheme
import com.goms.design_system.util.lockScreenOrientation
import com.goms.login.component.LoginText

@Composable
fun LoginRoute(
    onSignUpClick: () -> Unit,
    onInputLoginClick: () -> Unit
) {
    LoginScreen(
        onSignUpClick = onSignUpClick,
        onInputLoginClick = onInputLoginClick
    )
}

@Composable
fun LoginScreen(
    onSignUpClick: () -> Unit,
    onInputLoginClick: () -> Unit
) {
    lockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    GomsTheme { colors, typography ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.BACKGROUND)
                .padding(horizontal = 20.dp)
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))
            GomsIcon()
            Spacer(modifier = Modifier.height(48.dp))
            LoginText()
            Spacer(modifier = Modifier.height(136.dp))
            GomsButton(
                modifier = Modifier.fillMaxWidth(),
                text = "로그인",
                state = ButtonState.Normal
            ) {
                onInputLoginClick()
            }
            Spacer(modifier = Modifier.height(16.dp))
            LinkText(text = "회원가입") {
                onSignUpClick()
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}