package com.goms.network.dto.response.council

import com.goms.model.enum.Authority
import com.goms.model.enum.Gender
import com.goms.model.enum.Major
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StudentResponse(
    @Json(name = "accountIdx") val accountIdx: String,
    @Json(name = "name") val name: String,
    @Json(name = "grade") val grade: Int,
    @Json(name = "gender") val gender: Gender,
    @Json(name = "major") val major: Major,
    @Json(name = "profileUrl") val profileUrl: String?,
    @Json(name = "authority") val authority: Authority,
    @Json(name = "isBlackList") val isBlackList: Boolean,
    @Json(name = "isOuting") val isOuting: Boolean
)