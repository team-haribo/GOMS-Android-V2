package com.goms.qrcode.component

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.goms.design_system.component.button.ButtonState
import com.goms.design_system.component.button.GomsButton
import com.goms.design_system.component.lottie.AnimatedLottie
import com.goms.design_system.component.spacer.GomsSpacer
import com.goms.design_system.component.spacer.SpacerSize
import com.goms.design_system.theme.GomsTheme
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.theme.ThemeType
import com.goms.design_system.util.ThemePreviews

@Composable
internal fun QrcodeResultDialog(
    openDialog: Boolean,
    onStateChange: (Boolean) -> Unit,
    rawId: Int,
    isPlaying: Boolean,
    title: String,
    description: String,
    buttonText: String,
    onClick: () -> Unit,
) {
    var openDialog by remember { mutableStateOf(openDialog) }

    if (openDialog) {
        Dialog(
            onDismissRequest = { openDialog = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colors.BACKGROUND)
                    .padding(horizontal = 20.dp)
                    .navigationBarsPadding(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.weight(1f))
                AnimatedLottie(
                    rawId = rawId,
                    modifier = Modifier.size(150.dp),
                    isInfiniteRepetition = isPlaying
                )
                QrcodeResultText(
                    text = title,
                    modifier = Modifier.padding(top = 16.dp, bottom = 12.dp)
                )
                QrcodeResultDescriptionText(text = description)
                Spacer(modifier = Modifier.weight(1f))
                GomsButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = buttonText,
                    state = ButtonState.Normal
                ) {
                    onClick()
                    openDialog = false
                }
                GomsSpacer(size = SpacerSize.Medium)
            }
        }
    } else {
        onStateChange(openDialog)
    }
}

@ThemePreviews
@Composable
private fun QrcodeResultDialogPreview() {
    GomsTheme(ThemeType.SYSTEM.value) {
        QrcodeResultDialog(
            openDialog = true,
            onStateChange = {},
            rawId = 0,
            isPlaying = true,
            title = "GOMS",
            description = "곰곰곰",
            buttonText = "확인"
        ) {}
    }
}