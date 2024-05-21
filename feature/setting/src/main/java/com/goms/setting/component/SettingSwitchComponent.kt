package com.goms.setting.component

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

@Composable
internal fun SettingSwitchComponent(
    modifier: Modifier,
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

@Composable
@Preview(showBackground = true)
private fun SettingSwitchComponentPreview() {
    SettingSwitchComponent(
        modifier = Modifier.padding(20.dp),
        title = "외출제 푸시 알림",
        detail = "외출할 시간이 될 때마다 알려드려요",
        onFunctionOff = {},
        isSwitchOn = false
    ) {}
}