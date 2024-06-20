package com.goms.login

import android.content.pm.ActivityInfo
import android.content.res.Configuration
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.goms.design_system.component.button.ButtonState
import com.goms.design_system.component.button.GomsButton
import com.goms.design_system.component.spacer.GomsSpacer
import com.goms.design_system.component.spacer.SpacerSize
import com.goms.design_system.component.text.LinkText
import com.goms.design_system.icon.GomsIcon
import com.goms.design_system.theme.GomsTheme
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.theme.ThemeType
import com.goms.design_system.util.lockScreenOrientation
import com.goms.login.component.LoginText

@Composable
internal fun LoginRoute(
    onSignUpClick: () -> Unit,
    onInputLoginClick: () -> Unit
) {
    LoginScreen(
        onSignUpClick = onSignUpClick,
        onInputLoginClick = onInputLoginClick
    )
}

@Composable
private fun LoginScreen(
    onSignUpClick: () -> Unit,
    onInputLoginClick: () -> Unit
) {
    lockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.BACKGROUND)
            .padding(horizontal = 20.dp)
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(2f))
        GomsIcon()
        GomsSpacer(size = SpacerSize.ExtraLarge)
        LoginText()
        Spacer(modifier = Modifier.weight(1.1f))
        GomsButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.login),
            state = ButtonState.Normal
        ) {
            onInputLoginClick()
        }
        GomsSpacer(size = SpacerSize.Small)
        LinkText(text = stringResource(id = R.string.sign_up)) {
            onSignUpClick()
        }
        GomsSpacer(size = SpacerSize.Small)
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun LoginScreenPreview() {
    GomsTheme(ThemeType.SYSTEM.value) {
        LoginScreen(
            onSignUpClick = {}
        ) {}
    }
}