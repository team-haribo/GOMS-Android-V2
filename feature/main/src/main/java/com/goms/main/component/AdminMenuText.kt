package com.goms.main.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.goms.design_system.theme.GomsTheme

@Composable
fun AdminMenuText(modifier: Modifier) {
    Text(
        modifier = modifier,
        text = "관리자 메뉴",
        style = GomsTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        color = GomsTheme.colors.WHITE
    )
}