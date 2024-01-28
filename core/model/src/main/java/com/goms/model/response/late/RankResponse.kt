package com.goms.model.response.late

import com.goms.model.enum.Gender
import com.goms.model.enum.Major
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.UUID

@JsonClass(generateAdapter = true)
data class RankResponse(
    @Json(name = "accountIdx") val accountIdx: UUID,
    @Json(name = "name") val name: String,
    @Json(name = "grade") val grade: Int,
    @Json(name = "major") val major: Major,
    @Json(name = "gender") val gender: Gender,
    @Json(name = "profileUrl") val profileUrl: String?
)