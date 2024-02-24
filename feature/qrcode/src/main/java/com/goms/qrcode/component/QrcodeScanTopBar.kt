package com.goms.qrcode.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.goms.design_system.component.button.GomsBackButton
import com.goms.design_system.icon.CloseIcon
import com.goms.design_system.icon.GomsTextIcon
import com.goms.design_system.theme.GomsTheme

@Composable
fun QrcodeScanTopBar(
) {
    GomsTheme { color, _ ->
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            GomsTextIcon(tint = color.BLACK)
            Box(
                modifier = Modifier.size(64.dp, 56.dp),
                contentAlignment = Alignment.Center
                ) {
                CloseIcon(tint = color.BLACK)
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun QrcodeScanTopBarPreview() {
    QrcodeScanTopBar()
}