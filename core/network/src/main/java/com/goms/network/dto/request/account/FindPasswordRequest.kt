package com.goms.network.dto.request.account

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FindPasswordRequest(
    @Json(name = "email") val email: String,
    @Json(name = "newPassword") val password: String
)
