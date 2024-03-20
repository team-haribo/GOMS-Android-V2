package com.goms.design_system.component.button

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.goms.design_system.component.clickable.gomsClickable
import com.goms.design_system.icon.BackIcon
import com.goms.design_system.theme.GomsTheme
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.theme.GomsTheme.typography

@Composable
fun GomsBackButton(
    modifier: Modifier = Modifier.height(48.dp),
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .padding(start = 15.dp)
            .gomsClickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        BackIcon()
        Spacer(modifier = Modifier.width(7.dp))
        Text(
            text = "돌아가기",
            style = typography.textMedium,
            fontWeight = FontWeight.Normal,
            color = colors.I5
        )
    }
}
