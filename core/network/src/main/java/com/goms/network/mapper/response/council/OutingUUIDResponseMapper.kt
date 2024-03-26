package com.goms.network.mapper.response.council

import com.goms.model.response.council.OutingUUIDResponseModel
import com.goms.network.dto.response.council.OutingUUIDResponse

fun OutingUUIDResponse.toModel(): OutingUUIDResponseModel =
    OutingUUIDResponseModel(outingUUID = this.outingUUID)