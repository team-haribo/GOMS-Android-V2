package com.goms.model.response.council

import com.squareup.moshi.Json
import java.util.UUID

data class OutingUUIDResponse(
    @Json(name = "outingUUID") val outingUUID: String,
)
