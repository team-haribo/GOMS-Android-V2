package com.goms.model.request.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SendNumberRequest(
    @Json(name = "email") val email: String
)