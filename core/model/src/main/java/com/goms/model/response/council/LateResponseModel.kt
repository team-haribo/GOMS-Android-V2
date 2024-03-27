package com.goms.model.response.council

import com.goms.model.enum.Gender
import com.goms.model.enum.Major

data class LateResponseModel(
    val accountIdx: String,
    val name: String,
    val grade: Int,
    val major: Major,
    val gender: Gender,
    val profileUrl: String?
)