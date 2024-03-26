package com.goms.model.response.account

import com.goms.model.enum.Authority
import com.goms.model.enum.Gender
import com.goms.model.enum.Major

data class ProfileResponseModel(
    val name: String,
    val grade: Int,
    val major: Major,
    val gender: Gender,
    val authority: Authority,
    val profileUrl: String?,
    val lateCount: Int,
    val isOuting: Boolean,
    val isBlackList: Boolean
)
