package com.goms.setting.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

fun uriToJpeg(context: Context, uri: Uri): File? {
    val inputStream = context.contentResolver.openInputStream(uri) ?: return null
    val bitmap = BitmapFactory.decodeStream(inputStream)
    inputStream.close()

    val outputFile = createTempJpegFile(context) ?: return null

    val outputStream: OutputStream = FileOutputStream(outputFile)
    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
    outputStream.close()

    return outputFile
}

fun fileToMultipartFile(file: File): MultipartBody.Part {
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
