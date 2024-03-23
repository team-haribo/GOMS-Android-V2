package com.goms.design_system.component.text

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.goms.design_system.component.clickable.gomsClickable
import com.goms.design_system.theme.GomsTheme
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.theme.GomsTheme.typography

@Composable
fun RowLinkText(
    onLinkTextClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "비밀번호를 잊으셨나요?",
            style = typography.buttonLarge,
            fontWeight = FontWeight.Normal,
            color = colors.G4
        )
        Text(
            modifier = Modifier.gomsClickable { onLinkTextClick() },
            text = "비밀번호 찾기",
            style = typography.buttonLarge,
            fontWeight = FontWeight.Normal,
            color = colors.P5
        )
    }
}
