package com.goms.network.mapper.request.auth

import com.goms.model.request.auth.SignUpRequestModel
import com.goms.network.dto.request.auth.SignUpRequest

fun SignUpRequestModel.toDto(): SignUpRequest =
    SignUpRequest(
        email = this.email,
        password = this.password,
        name = this.name,
        gender = this.gender,
        major = this.major
    )