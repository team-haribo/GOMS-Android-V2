package com.goms.qrcode.component

import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import com.goms.qrcode.util.setupQrScanCamera

@androidx.camera.core.ExperimentalGetImage
@Composable
internal fun QrcodeScanView(
    modifier: Modifier = Modifier,
    lifecycleOwner: LifecycleOwner,
    onQrcodeScan: (String) -> Unit,
) {
    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { context ->
            PreviewView(context).apply {
                post {
                    setupQrScanCamera(
                        previewView = this,
                        lifecycleOwner = lifecycleOwner,
                        onQrcodeScanned = onQrcodeScan
                    )
                }
            }
        },
    )
}
