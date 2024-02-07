package com.goms.qrcode_scan.component

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.goms.design_system.theme.GomsTheme
import com.goms.model.enum.Authority
import com.goms.ui.GomsFloatingButton

@Composable
fun QrcodeScanGuide() {
    GomsTheme { _, _ ->
        QrcodeScanGuide()
    }
}

@Composable
@Preview(showBackground = true)
fun QrcodeScanGuidePreview() {
    QrcodeScanGuide()
}