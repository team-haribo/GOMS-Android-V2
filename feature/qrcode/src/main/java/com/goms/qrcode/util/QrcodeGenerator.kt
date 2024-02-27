package com.goms.qrcode.util

import android.graphics.Bitmap
import android.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun QrcodeGenerator(
    content: String,
    size: Dp = 200.dp,
    padding: Dp = 0.dp
): BitmapPainter {

    val density = LocalDensity.current
    val size = with(density) { size.roundToPx() }
    val padding = with(density) { padding.roundToPx() }


    var bitmap by remember(content) { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(bitmap) {
        if (bitmap != null) return@LaunchedEffect

        launch(Dispatchers.IO) {
            val qrCodeWriter = QRCodeWriter()

            val encodeHints = mutableMapOf<EncodeHintType, Any?>()
                .apply {
                    this[EncodeHintType.MARGIN] = padding
                }

            val bitmapMatrix = try {
                qrCodeWriter.encode(
                    content, BarcodeFormat.QR_CODE,
                    size, size, encodeHints
                )
            } catch (ex: WriterException) {
                null
            }

            val matrixWidth = bitmapMatrix?.width ?: size
            val matrixHeight = bitmapMatrix?.height ?: size

            val newBitmap = Bitmap.createBitmap(
                bitmapMatrix?.width ?: size,
                bitmapMatrix?.height ?: size,
                Bitmap.Config.ARGB_8888,
            )

            val pixels = IntArray(matrixWidth * matrixHeight)

            for (x in 0 until matrixWidth) {
                for (y in 0 until matrixHeight) {
                    val shouldColorPixel = bitmapMatrix?.get(x, y) ?: false
                    val pixelColor = if (shouldColorPixel) Color.BLACK else Color.WHITE

                    pixels[y * matrixWidth + x] = pixelColor
                }
            }

            newBitmap.setPixels(pixels, 0, matrixWidth, 0, 0, matrixWidth, matrixHeight)

            bitmap = newBitmap
        }
    }

    return remember(bitmap) {
        val currentBitmap = bitmap ?: Bitmap.createBitmap(
            size, size,
            Bitmap.Config.ARGB_8888,
        ).apply { eraseColor(Color.TRANSPARENT) }

        BitmapPainter(currentBitmap.asImageBitmap())
    }
}
