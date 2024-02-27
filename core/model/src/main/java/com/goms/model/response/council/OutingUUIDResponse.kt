package com.goms.model.response.council

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.UUID
@JsonClass(generateAdapter = true)
data class OutingUUIDResponse(
    @Json(name = "outingUUID") val outingUUID: String,
)
