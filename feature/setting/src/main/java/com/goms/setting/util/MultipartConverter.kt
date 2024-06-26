package com.goms.setting.util

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

private fun uriToJpeg(context: Context, uri: Uri): File? {
    val inputStream = context.contentResolver.openInputStream(uri) ?: return null
    val bitmap = getExifData(context = context, uri = uri)?.let { exifData ->
        rotateImage(BitmapFactory.decodeStream(inputStream), exifData)
    } ?: BitmapFactory.decodeStream(inputStream)
    inputStream.close()

    val outputFile = createTempJpegFile(context) ?: return null

    val outputStream: OutputStream = FileOutputStream(outputFile)
    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
    outputStream.close()
    bitmap.recycle()

    return outputFile
}
private fun fileToMultipartFile(file: File): MultipartBody.Part {
    val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())

    return MultipartBody.Part.createFormData("File", file.name, requestFile)
}

fun getMultipartFile(context: Context, uri: Uri): MultipartBody.Part? {
    val jpegFile = uriToJpeg(context, uri) ?: return null

    return fileToMultipartFile(jpegFile)
}

@Throws(IOException::class)
private fun createTempJpegFile(context: Context): File? {
    val outputDirectory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(
        "image_",
        ".jpg",
        outputDirectory
    )
}
