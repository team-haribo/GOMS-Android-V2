package com.goms.main.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.goms.design_system.theme.GomsTheme

@Composable
fun LateListText(modifier: Modifier) {
    GomsTheme { colors, typography ->
        Text(
            modifier = modifier,
            text = "외출 현황",
            style = typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = colors.WHITE
        )
    }
}