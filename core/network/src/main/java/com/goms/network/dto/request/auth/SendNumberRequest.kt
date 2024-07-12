package com.goms.network.dto.request.auth

import com.goms.model.enum.EmailStatus
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SendNumberRequest(
    @Json(name = "email") val email: String,
    @Json(name = "emailStatus") val emailStatus: EmailStatus,
)