package com.goms.network.dto.response.council

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OutingUUIDResponse(
    @Json(name = "outingUUID") val outingUUID: String,
)
