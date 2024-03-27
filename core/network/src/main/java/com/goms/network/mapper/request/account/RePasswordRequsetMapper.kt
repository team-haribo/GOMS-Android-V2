package com.goms.network.mapper.request.account

import com.goms.model.request.account.RePasswordRequestModel
import com.goms.network.dto.request.account.RePasswordRequest

fun RePasswordRequestModel.toDto(): RePasswordRequest =
    RePasswordRequest(
        email = this.email,
        password = this.password
    )