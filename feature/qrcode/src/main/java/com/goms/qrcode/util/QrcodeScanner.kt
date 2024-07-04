package com.goms.qrcode.util

import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

class QrcodeScanner(
    val qrcodeData: (String?) -> Unit
) : ImageAnalysis.Analyzer {

    private val scanner = BarcodeScanning.getClient(
        BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .build()
    )
    @androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        mediaImage?.let {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    val qrCodeValues = mutableListOf<String>()
                    for (barcode in barcodes) {
                        barcode.displayValue?.let { qrCodeValues.add(it) }
                    }
                    if (qrCodeValues.isNotEmpty()) {
                        qrcodeData(qrCodeValues.joinToString(", "))
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