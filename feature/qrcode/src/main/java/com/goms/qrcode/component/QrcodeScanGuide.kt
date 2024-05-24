package com.goms.qrcode.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.goms.design_system.icon.QrScanGuideIcon
import com.goms.design_system.theme.GomsTheme

@Composable
internal fun QrcodeScanGuide() {
    QrScanGuideIcon()
}

@Composable
@Preview(showBackground = true)
private fun QrcodeScanGuidePreview() {
    QrcodeScanGuide()
}