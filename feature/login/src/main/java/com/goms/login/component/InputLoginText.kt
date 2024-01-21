package com.goms.login.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.goms.design_system.theme.GomsTheme

@Composable
fun InputLoginText(modifier: Modifier) {
    GomsTheme { colors, typography ->
        Text(
            modifier = modifier,
            text = "로그인",
            style = typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = colors.WHITE
        )
    }
}