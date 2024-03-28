package com.goms.model.response.outing

import com.goms.model.enum.Gender
import com.goms.model.enum.Major

data class OutingResponseModel(
    val accountIdx: String,
    val name: String,
    val major: Major,
    val grade: Int,
    val gender: Gender,
    val profileUrl: String?,
    val createdTime: String
)