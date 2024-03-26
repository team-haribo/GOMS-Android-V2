package com.goms.model.response.council

import com.goms.model.enum.Authority
import com.goms.model.enum.Gender
import com.goms.model.enum.Major

data class StudentResponseModel(
    val accountIdx: String,
    val name: String,
    val grade: Int,
    val gender: Gender,
    val major: Major,
    val profileUrl: String?,
    val authority: Authority,
    val isBlackList: Boolean
)