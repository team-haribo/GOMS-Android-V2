package com.goms.network.dto.response.outing

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CountResponse(
    @Json(name = "outingCount") val outingCount: Int
)