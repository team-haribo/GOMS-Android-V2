package com.goms.network.dto.request.account

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RePasswordRequest(
    @Json(name = "password") val password: String,
    @Json(name = "newPassword") val newPassword: String
)
