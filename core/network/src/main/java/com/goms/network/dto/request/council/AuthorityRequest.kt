package com.goms.network.dto.request.council

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthorityRequest(
    @Json(name = "accountIdx") val accountIdx: String,
    @Json(name = "authority") val authority: String
)