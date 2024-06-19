package com.goms.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.goms.design_system.component.clickable.gomsClickable
import com.goms.design_system.icon.GomsTextIcon
import com.goms.design_system.icon.MenuIcon
import com.goms.design_system.icon.SettingIcon
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.util.gomsPreview

@Composable
fun GomsTopBar(
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
    onSettingClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(end = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        GomsTextIcon(tint = colors.G4)
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .gomsClickable(
                    isIndication = true,
                    rippleColor = colors.G7.copy(0.5f)
                ) {
                    onSettingClick()
                },
            contentAlignment = Alignment.Center
        ) {
            icon()
        }
    }
}

@Preview
@Composable
fun GomsTopBarPreview() {
    gomsPreview {
        Column {
            GomsTopBar(icon = { SettingIcon() }) {}
            GomsTopBar(icon = { MenuIcon() }) {}
        }
    }
}