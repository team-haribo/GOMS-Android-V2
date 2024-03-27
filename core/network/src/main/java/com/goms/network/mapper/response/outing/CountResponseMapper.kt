package com.goms.network.mapper.response.outing

import com.goms.model.response.outing.CountResponseModel
import com.goms.network.dto.response.outing.CountResponse

fun CountResponse.toModel(): CountResponseModel =
    CountResponseModel(outingCount = this.outingCount)