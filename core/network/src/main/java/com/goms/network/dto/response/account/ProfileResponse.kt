package com.goms.network.dto.response.account

import com.goms.model.enum.Authority
import com.goms.model.enum.Gender
import com.goms.model.enum.Major
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProfileResponse(
    @Json(name = "name") val name: String,
    @Json(name = "grade") val grade: Int,
    @Json(name = "major") val major: Major,
    @Json(name = "gender") val gender: Gender,
    @Json(name = "authority") val authority: Authority,
    @Json(name = "profileUrl") val profileUrl: String?,
    @Json(name = "lateCount") val lateCount: Int,
    @Json(name = "isOuting") val isOuting: Boolean,
    @Json(name = "isBlackList") val isBlackList: Boolean
)
