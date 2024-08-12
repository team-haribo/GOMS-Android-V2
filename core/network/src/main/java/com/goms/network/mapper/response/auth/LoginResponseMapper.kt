package com.goms.network.mapper.response.auth

import com.goms.model.response.auth.LoginResponseModel
import com.goms.network.dto.response.auth.LoginResponse

fun LoginResponse.toModel(): LoginResponseModel =
    LoginResponseModel(
        accessToken = this.accessToken,
        refreshToken = this.refreshToken,
        accessTokenExp = this.accessTokenExp,
        refreshTokenExp = this.refreshTokenExp,
        authority = this.authority
    )