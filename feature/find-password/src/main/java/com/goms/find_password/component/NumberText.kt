package com.goms.find_password.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.theme.GomsTheme.typography

@Composable
fun NumberText(modifier: Modifier) {
    Text(
        modifier = modifier,
        text = "인증번호 입력",
        style = typography.titleLarge,
        fontWeight = FontWeight.Bold,
        color = colors.WHITE
    )
}
