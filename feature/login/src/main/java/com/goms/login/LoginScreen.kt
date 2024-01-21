package com.goms.login

import android.content.pm.ActivityInfo
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.goms.common.result.Result
import com.goms.design_system.component.button.AuthButton
import com.goms.design_system.component.button.ButtonState
import com.goms.design_system.component.button.GomsButton
import com.goms.design_system.component.text.LinkText
import com.goms.design_system.component.view.GAuthWebView
import com.goms.design_system.icon.GomsIcon
import com.goms.design_system.theme.GomsTheme
import com.goms.design_system.util.lockScreenOrientation
import com.goms.login.component.LoginText
import com.goms.login.viewmodel.AuthViewModel
import com.goms.model.request.auth.LoginRequest

@Composable
fun LoginRoute(
    onSignUpClick: () -> Unit,
    onInputLoginClick: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    LoginScreen(
        onSignUpClick = onSignUpClick,
        onInputLoginClick = onInputLoginClick,
        loginCallBack = { code ->
            viewModel.login(body = LoginRequest(code))
        }
    )
}

suspend fun login(viewModel: AuthViewModel) {
    viewModel.loginResponse.collect {
        when (it) {
            is Result.Success -> {
                viewModel.saveToken(token = it.data)
            }
            else -> {}
        }
    }
}

@Composable
fun LoginScreen(
    onSignUpClick: () -> Unit,
    onInputLoginClick: () -> Unit,
    loginCallBack: (code: String) -> Unit
) {
    lockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
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