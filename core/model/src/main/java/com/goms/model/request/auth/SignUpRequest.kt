package com.goms.model.request.auth

import com.goms.model.enum.Gender
import com.goms.model.enum.Major
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignUpRequest(
    @Json(name = "email") val email: String,
    @Json(name = "password") val password: String,
    @Json(name = "name") val name: String,
    @Json(name = "gender") val gender: String,
    @Json(name = "major") val major: String
)
