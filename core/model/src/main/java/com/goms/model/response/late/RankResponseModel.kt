package com.goms.model.response.late

import com.goms.model.enum.Gender
import com.goms.model.enum.Major

data class RankResponseModel(
    val accountIdx: String,
    val name: String,
    val grade: Int,
    val major: Major,
    val gender: Gender,
    val profileUrl: String?
)