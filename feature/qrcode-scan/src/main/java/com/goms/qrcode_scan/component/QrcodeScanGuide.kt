package com.goms.qrcode_scan.component

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.goms.design_system.theme.GomsTheme
<<<<<<< HEAD
import com.goms.design_system.icon.QrScanGuideIcon
@Composable
fun QrcodeScanGuide() {
    GomsTheme { _, _ ->
        QrScanGuideIcon()
=======
import com.goms.model.enum.Authority
import com.goms.ui.GomsFloatingButton

@Composable
fun QrcodeScanGuide() {
    GomsTheme { _, _ ->
        QrcodeScanGuide()
>>>>>>> 9d1ba91 (:memo: :: Add QrcodeScanGuide.kt)
    }
}

@Composable
@Preview(showBackground = true)
fun QrcodeScanGuidePreview() {
    QrcodeScanGuide()
}