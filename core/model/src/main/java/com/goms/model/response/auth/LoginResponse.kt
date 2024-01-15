package com.goms.model.response.auth

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExp: String,
    val refreshTokenExp: String,
    val authority: String
)