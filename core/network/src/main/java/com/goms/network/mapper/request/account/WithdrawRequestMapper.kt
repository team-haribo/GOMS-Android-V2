package com.goms.network.mapper.request.account

import com.goms.model.request.account.WithdrawRequestModel
import com.goms.network.dto.request.account.WithdrawRequest

fun WithdrawRequestModel.toDto(): WithdrawRequest =
    WithdrawRequest(
        password = this.password
    )