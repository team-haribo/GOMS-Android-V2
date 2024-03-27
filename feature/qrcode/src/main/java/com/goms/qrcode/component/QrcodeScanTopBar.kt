package com.goms.qrcode.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.goms.design_system.component.clickable.gomsClickable
import com.goms.design_system.icon.CloseIcon
import com.goms.design_system.icon.GomsTextIcon
import com.goms.design_system.theme.GomsTheme

@Composable
fun QrcodeScanTopBar(
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        GomsTextIcon(tint = Color.White)
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .gomsClickable(isIndication = true) {
                    onClick()
                },
            contentAlignment = Alignment.Center
        ) {
            CloseIcon(tint = Color.White)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun QrcodeScanTopBarPreview() {
    QrcodeScanTopBar(onClick = {})
}