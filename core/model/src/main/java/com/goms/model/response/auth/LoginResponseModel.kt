package com.goms.model.response.auth

import com.goms.model.enum.Authority

data class LoginResponseModel(
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExp: String,
    val refreshTokenExp: String,
    val authority: Authority
)