package com.goms.network.mapper.request.account

import com.goms.model.request.account.FindPasswordRequestModel
import com.goms.network.dto.request.account.FindPasswordRequest

fun FindPasswordRequestModel.toDto(): FindPasswordRequest =
    FindPasswordRequest(
        email = this.email,
        password = this.password
    )