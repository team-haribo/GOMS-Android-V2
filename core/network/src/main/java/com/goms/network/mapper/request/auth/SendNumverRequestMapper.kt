package com.goms.network.mapper.request.auth

import com.goms.model.request.auth.SendNumberRequestModel
import com.goms.network.dto.request.auth.SendNumberRequest

fun SendNumberRequestModel.toDto(): SendNumberRequest =
    SendNumberRequest(
        email = this.email,
        emailStatus = this.emailStatus
    )