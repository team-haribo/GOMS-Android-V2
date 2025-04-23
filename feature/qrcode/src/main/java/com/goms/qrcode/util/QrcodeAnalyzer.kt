package com.goms.qrcode.util

import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

internal class QrcodeAnalyzer(
    private val qrcodeData: (String) -> Unit
) : ImageAnalysis.Analyzer {

    private val scanner = BarcodeScanning.getClient(
        BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .build()
    )

    // 마지막으로 인식된 시간 (ms)
    private var lastScanTime = 0L

    @androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image ?: run {
            imageProxy.close()
            return
        }

        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

        scanner.process(image)
            .addOnSuccessListener { barcodes ->
                val currentTime = System.currentTimeMillis()
                val firstQrCode = barcodes.firstOrNull()?.displayValue

                // 10초(=10000ms) 쿨타임 체크
                if (firstQrCode != null && currentTime - lastScanTime > 10_000) {
                    Log.d("QrcodeAnalyzer", "QR 인식: $firstQrCode")
                    lastScanTime = currentTime
                    qrcodeData(firstQrCode)
                } else {
                    if (firstQrCode != null) {
                        Log.d("QrcodeAnalyzer", "10초 내 중복 인식 차단됨")
                    }
                }
            }
            .addOnFailureListener {
                Log.d("QrcodeAnalyzer", "QR 코드 스캔 실패", it)
            }
            .addOnCompleteListener {
                imageProxy.close()
            }
    }
}