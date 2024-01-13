package com.goms.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.goms.design_system.component.button.AuthButton
import com.goms.design_system.component.text.LinkText
import com.goms.design_system.component.view.GAuthWebView
import com.goms.design_system.icon.GomsIcon
import com.goms.design_system.theme.GomsTheme
import com.goms.login.component.LoginText

@Composable
fun LoginRoute(
    onNumberLoginClick: () -> Unit
) {
    LoginScreen(
        onNumberLoginClick = onNumberLoginClick,
        loginCallBack = {}
    )
}

@Composable
fun LoginScreen(
    onNumberLoginClick: () -> Unit,
    loginCallBack: (code: String) -> Unit
) {
    var webViewVisible by remember { mutableStateOf(false) }

    GomsTheme { colors, typography ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.BLACK)
                .padding(horizontal = 20.dp)
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))
            GomsIcon()
            Spacer(modifier = Modifier.height(48.dp))
            LoginText()
            Spacer(modifier = Modifier.height(136.dp))
            AuthButton {
                webViewVisible = true
            }
            Spacer(modifier = Modifier.height(16.dp))
            LinkText(text = "인증번호로 로그인하기") {
                onNumberLoginClick()
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        GAuthWebView(
            webViewVisible = webViewVisible,
            onChangeWebViewVisible = { webViewVisible = it },
            loginCallBack = { loginCallBack(it) }
        )
    }
}