package com.goms.qrcode_scan.component

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.goms.design_system.theme.GomsTheme
import com.goms.qrcode_scan.util.QrcodeScanner
import java.util.concurrent.Executors

@androidx.camera.core.ExperimentalGetImage
@Composable
fun QrcodeScanPreview(
    context: Context,
    onQrcodeScan: (String?) -> Unit
) {
    GomsTheme { _, _ ->
        Scaffold(
            modifier = Modifier.fillMaxSize(),
        ) { innerPadding: PaddingValues ->
            AndroidView({ context ->
                val cameraExecutor = Executors.newSingleThreadExecutor()
                val previewView = PreviewView(context).also {
                    it.scaleType = PreviewView.ScaleType.FILL_CENTER
                }
                val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
                cameraProviderFuture.addListener({
                    val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

                    val preview = Preview.Builder()
                        .build()
                        .also {
                            it.setSurfaceProvider(previewView.surfaceProvider)
                        }

                    val imageCapture = ImageCapture.Builder().build()

                    val imageAnalyzer = ImageAnalysis.Builder()
                        .build()
                        .also {
                            it.setAnalyzer(cameraExecutor, QrcodeScanner { qrcodeData ->
                                Toast.makeText(context, qrcodeData, Toast.LENGTH_SHORT).show()
                                onQrcodeScan(qrcodeData)
                            })
                        }

                    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                    try {
                        cameraProvider.unbindAll()

                        cameraProvider.bindToLifecycle(
                            context as ComponentActivity, cameraSelector, preview, imageCapture, imageAnalyzer)

                    } catch(exc: Exception) {
                        Log.d("qrcode", "binding failed")
                    }
                }, ContextCompat.getMainExecutor(context))
                previewView
            },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding))
        }
    }
}