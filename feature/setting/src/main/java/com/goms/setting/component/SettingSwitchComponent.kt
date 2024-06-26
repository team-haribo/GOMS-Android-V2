package com.goms.setting.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.goms.design_system.component.button.GomsSwitchButton
import com.goms.design_system.theme.GomsTheme
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.theme.GomsTheme.typography
import com.goms.design_system.theme.ThemeType

@Composable
internal fun SettingSwitchComponent(
    modifier: Modifier = Modifier,
    title: String,
    detail: String,
    isSwitchOn: Boolean,
    switchOnBackground: Color = Color.Unspecified,
    switchOffBackground: Color = Color.Unspecified,
    onFunctionOff: () -> Unit,
    onFunctionOn: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = title,
                style = typography.textMedium,
                color = colors.WHITE,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = detail,
                style = typography.caption,
                color = colors.G4,
                fontWeight = FontWeight.Normal
            )
        }
        GomsSwitchButton(
            stateOn = 1,
            stateOff = 0,
            switchOnBackground = switchOnBackground,
            switchOffBackground = switchOffBackground,
            initialValue = if (isSwitchOn) 1 else 0,
            onCheckedChanged = {
                if (it) onFunctionOn() else onFunctionOff()
            }
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun SettingSwitchComponentPreview() {
    GomsTheme(ThemeType.SYSTEM.value) {
        SettingSwitchComponent(
            modifier = Modifier.padding(20.dp),
            title = "곰",
            detail = "식육목 곰과에 속하는 포유동물",
            onFunctionOff = {},
            isSwitchOn = true
        ) {}
    }
}