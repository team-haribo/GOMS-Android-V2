package com.goms.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.goms.design_system.component.modifier.gomsClickable
import com.goms.design_system.icon.GomsTextIcon
import com.goms.design_system.icon.PersonIcon
import com.goms.design_system.icon.SettingIcon
import com.goms.design_system.theme.GomsTheme
import com.goms.model.enum.Authority

@Composable
fun GomsTopBar(
    modifier: Modifier = Modifier,
    role: Authority,
    icon: @Composable () -> Unit,
    onSettingClick: () -> Unit,
    onAdminClick: () -> Unit
) {
    var backgroundColor by remember { mutableStateOf(Color.Transparent) }

    GomsTheme { colors, typography ->
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(end = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            GomsTextIcon(tint = if (role == Authority.ROLE_STUDENT) colors.P5 else colors.A7)
            Spacer(modifier = Modifier.weight(1f))
            if (role == Authority.ROLE_STUDENT_COUNCIL) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(backgroundColor)
                        .gomsClickable(isIndication = true) {
                            backgroundColor = colors.G7.copy(0.5f)
                            onAdminClick()
                            backgroundColor = Color.Transparent
                        }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        PersonIcon(tint = colors.G7)
                        Text(
                            text = "학생 관리",
                            style = typography.buttonSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = colors.G7
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.width(32.dp))
            IconButton(onClick = { onSettingClick() }) {
                icon()
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun GomsTopBarPreview() {
    Column {
        GomsTopBar(role = Authority.ROLE_STUDENT, icon = { SettingIcon() }, onSettingClick = {}) {}
        GomsTopBar(role = Authority.ROLE_STUDENT_COUNCIL, icon = { SettingIcon() }, onSettingClick = {}) {}
    }
}