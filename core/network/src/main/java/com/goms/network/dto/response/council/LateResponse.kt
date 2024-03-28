package com.goms.network.dto.response.council

import com.goms.model.enum.Gender
import com.goms.model.enum.Major
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LateResponse(
    @Json(name = "accountIdx") val accountIdx: String,
    @Json(name = "name") val name: String,
    @Json(name = "grade") val grade: Int,
    @Json(name = "major") val major: Major,
    @Json(name = "gender") val gender: Gender,
    @Json(name = "profileUrl") val profileUrl: String?
)