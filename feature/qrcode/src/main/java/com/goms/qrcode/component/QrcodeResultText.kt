package com.goms.qrcode.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.theme.GomsTheme.typography

@Composable
internal fun QrcodeResultText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = typography.titleMedium,
        color = colors.WHITE,
        fontWeight = FontWeight.Bold,
        modifier = modifier
    )
}