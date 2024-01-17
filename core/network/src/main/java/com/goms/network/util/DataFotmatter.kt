package com.goms.network.util

import android.annotation.SuppressLint
import com.goms.common.exception.TokenExpirationException
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("SimpleDateFormat")
fun String.toDate(): Date {
    kotlin.runCatching {
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(this)!!
    }.onSuccess {
        return it
    }
    throw TokenExpirationException()
}

@SuppressLint("SimpleDateFormat")
fun Long.toGomsTimeDate(): Date {
    return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(this).toDate()
}