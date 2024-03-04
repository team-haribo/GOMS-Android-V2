package com.goms.ui

import android.content.Context
import android.widget.Toast

fun createToast(
    context: Context,
    message: String
) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}