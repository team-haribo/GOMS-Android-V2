package com.goms.design_system.component.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.goms.design_system.BuildConfig
import com.msg.gauthsignin.GAuthSigninWebView

@Composable
fun GAuthWebView(
    webViewVisible: Boolean,
    onChangeWebViewVisible: (webViewVisible: Boolean) -> Unit,
    loginCallBack: (code: String) -> Unit
) {
    AnimatedVisibility(
        visible = webViewVisible,
        modifier = Modifier.fillMaxSize(),
        enter = slideInVertically {
            -it
        },
        exit = slideOutVertically {
            -it
        }
    ) {
        GAuthSigninWebView(
            clientId = BuildConfig.CLIENT_ID,
            redirectUri = BuildConfig.REDIRECT_URL,
            onBackPressed = { onChangeWebViewVisible(false) },
            callBack = { loginCallBack(it) }
        )
    }
}