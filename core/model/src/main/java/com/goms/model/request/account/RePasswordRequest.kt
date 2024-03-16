package com.goms.model.request.account

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RePasswordRequest(
    @Json(name = "email") val email: String,
    @Json(name = "password") val password: String
)
