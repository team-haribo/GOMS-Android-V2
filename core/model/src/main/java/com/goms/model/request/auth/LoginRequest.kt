package com.goms.model.request.auth

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class LoginRequest(
    @Json(name = "code") val code: String
)