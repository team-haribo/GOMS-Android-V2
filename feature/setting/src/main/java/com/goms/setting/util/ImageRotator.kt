package com.goms.setting.util

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.provider.MediaStore

fun rotateImage(bitmap: Bitmap, exif: String): Bitmap {
    val matrix = Matrix()
    val angle = when (exif) {
        ExifInterface.ORIENTATION_ROTATE_90.toString() -> 90f
        ExifInterface.ORIENTATION_ROTATE_180.toString() -> 180f
        ExifInterface.ORIENTATION_ROTATE_270.toString() -> 270f
        else -> 0f
    }
    matrix.postRotate(angle)
    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
}

fun getExifData(context: Context, uri: Uri): String? {
    var exifData: String? = null
    var cursor: Cursor? = null
    try {
        val projection = arrayOf(MediaStore.Images.ImageColumns.DATA)
        cursor = context.contentResolver.query(uri, projection, null, null, null)
        if (cursor != null && cursor.moveToFirst()) {
            val filePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATA))
            exifData = getExifFromFile(filePath)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        cursor?.close()
    }
    return exifData
}

private fun getExifFromFile(filePath: String): String {
    val exif = ExifInterface(filePath)
    val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)

    val setOrientation = when (orientation) {
        ExifInterface.ORIENTATION_UNDEFINED -> ExifInterface.ORIENTATION_UNDEFINED
        ExifInterface.ORIENTATION_ROTATE_90 -> ExifInterface.ORIENTATION_ROTATE_90
        ExifInterface.ORIENTATION_ROTATE_180 -> ExifInterface.ORIENTATION_ROTATE_180
        ExifInterface.ORIENTATION_ROTATE_270 -> ExifInterface.ORIENTATION_ROTATE_270
        else -> ExifInterface.ORIENTATION_NORMAL
    }

    return setOrientation.toString()
}