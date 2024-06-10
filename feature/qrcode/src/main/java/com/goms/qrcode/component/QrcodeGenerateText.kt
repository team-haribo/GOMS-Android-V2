package com.goms.qrcode.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.theme.GomsTheme.typography
import com.goms.qrcode.R

@Composable
internal fun QrcodeGenerateText(
    modifier: Modifier
) {
    Box(
        modifier = modifier.padding(20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.outing_qrcode),
            style = typography.titleLarge,
            color = colors.WHITE,
            fontWeight = FontWeight.Bold
        )
    }
}