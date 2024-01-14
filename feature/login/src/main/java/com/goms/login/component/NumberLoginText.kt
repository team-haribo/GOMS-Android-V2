package com.goms.login.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import com.goms.design_system.theme.GomsTheme

@Composable
fun NumberLoginText() {
    GomsTheme { colors, typography ->
        Text(
            text = "인증번호로 로그인하기",
            style = typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
            color = colors.WHITE
        )
    }
}