package com.goms.setting.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.goms.design_system.component.clickable.gomsClickable
import com.goms.design_system.icon.ChevronRightIcon
import com.goms.design_system.theme.GomsTheme
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.theme.GomsTheme.typography

@Composable
fun PasswordChangeButton(
    modifier: Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .gomsClickable(
                isIndication = true,
                rippleColor = colors.G7.copy(0.5f)
            ) {
                onClick()
            }
    ) {
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = colors.WHITE.copy(0.15f)
        )
        Spacer(modifier = Modifier.height(22.dp))
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "비밀번호 재설정",
                style = typography.textMedium,
                fontWeight = FontWeight.SemiBold,
                color = colors.WHITE
            )
            ChevronRightIcon(
                tint = colors.WHITE
            )
        }
        Spacer(modifier = Modifier.height(22.dp))
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = colors.WHITE.copy(0.15f)
        )
    }
}
