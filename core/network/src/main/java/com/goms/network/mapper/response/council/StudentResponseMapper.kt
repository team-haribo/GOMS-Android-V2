package com.goms.network.mapper.response.council

import com.goms.model.response.council.StudentResponseModel
import com.goms.network.dto.response.council.StudentResponse

fun StudentResponse.toModel(): StudentResponseModel =
    StudentResponseModel(
        accountIdx = this.accountIdx,
        name = this.name,
        grade = this.grade,
        gender = this.gender,
        major = this.major,
        profileUrl = this.profileUrl,
        authority = this.authority,
        isBlackList = this.isBlackList,
        isOuting = this.isOuting
    )