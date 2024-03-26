package com.goms.network.mapper.response.council

import com.goms.model.response.council.LateResponseModel
import com.goms.network.dto.response.council.LateResponse

fun LateResponse.toModel(): LateResponseModel =
    LateResponseModel(
        accountIdx = this.accountIdx,
        name = this.name,
        grade = this.grade,
        major = this.major,
        gender = this.gender,
        profileUrl = this.profileUrl
    )