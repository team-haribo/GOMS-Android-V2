package com.goms.network.mapper.response.late

import com.goms.model.response.late.RankResponseModel
import com.goms.network.dto.response.late.RankResponse

fun RankResponse.toModel(): RankResponseModel =
    RankResponseModel(
        accountIdx = this.accountIdx,
        name = this.name,
        grade = this.grade,
        major = this.major,
        gender = this.gender,
        profileUrl = this.profileUrl
    )