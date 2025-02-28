package com.goms.qrcode.util

import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.ZoomSuggestionOptions
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

class QrcodeScanner(
    val qrcodeData: (String?) -> Unit,
    val isScanningEnabled: () -> Boolean
) : ImageAnalysis.Analyzer {

    private val scanner = BarcodeScanning.getClient(
        BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .build()
    )
    @androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        if (!isScanningEnabled()) {
            imageProxy.close()
            return
        }

        val mediaImage = imageProxy.toBitmap()
        mediaImage?.let {
            val image = InputImage.fromBitmap(mediaImage, imageProxy.imageInfo.rotationDegrees)

            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    for (barcode in barcodes) {
                        barcode.displayValue?.let {
                            qrcodeData(it)
                        }
                    }
                }
                .addOnFailureListener {
                    Log.d("qrcode","scan fail")
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        }
        imageProxy.close()
    }
}