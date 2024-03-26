package com.goms.network.mapper.response.account

import com.goms.model.response.account.ProfileResponseModel
import com.goms.network.dto.response.account.ProfileResponse

fun ProfileResponse.toModel(): ProfileResponseModel =
    ProfileResponseModel(
        name = this.name,
        grade = this.grade,
        major = this.major,
        gender = this.gender,
        authority = this.authority,
        profileUrl = this.profileUrl,
        lateCount = this.lateCount,
        isOuting = this.isOuting,
        isBlackList = this.isBlackList
    )