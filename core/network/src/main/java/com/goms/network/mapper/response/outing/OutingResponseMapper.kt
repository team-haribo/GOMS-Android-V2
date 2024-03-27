package com.goms.network.mapper.response.outing

import com.goms.model.response.outing.OutingResponseModel
import com.goms.network.dto.response.outing.OutingResponse

fun OutingResponse.toModel(): OutingResponseModel =
    OutingResponseModel(
        accountIdx = this.accountIdx,
        name = this.name,
        major = this.major,
        grade = this.grade,
        gender = this.gender,
        profileUrl = this.profileUrl,
        createdTime = this.createdTime
    )