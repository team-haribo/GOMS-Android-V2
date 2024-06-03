package com.goms.network.dto.request.account

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WithdrawRequest(
    @Json(name = "password") val password: String,
)
