package com.goms.qrcode_scan.component

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.goms.design_system.theme.GomsTheme
import com.goms.design_system.icon.QrScanGuideIcon
@Composable
fun QrcodeScanGuide() {
    GomsTheme { _, _ ->
        QrScanGuideIcon()
    }
}

@Composable
@Preview(showBackground = true)
fun QrcodeScanGuidePreview() {
    QrcodeScanGuide()
}