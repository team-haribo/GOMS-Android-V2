package com.goms.network.mapper.request.auth

import com.goms.model.request.auth.LoginRequestModel
import com.goms.network.dto.request.auth.LoginRequest

fun LoginRequestModel.toDto(): LoginRequest =
    LoginRequest(
        email = this.email,
        password = this.password
    )