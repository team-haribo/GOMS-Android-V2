package com.goms.main.data

import androidx.compose.runtime.Immutable
import com.goms.model.enum.Gender
import com.goms.model.enum.Major
import com.goms.model.response.council.LateResponse

@Immutable
data class LateData(
    val accountIdx: String,
    val name: String,
    val grade: Int,
    val major: Major,
    val gender: Gender,
    val profileUrl: String?
)

fun LateResponse.toData() =
    LateData(
        accountIdx = accountIdx,
        name = name,
        grade = grade,
        major = major,
        gender = gender,
        profileUrl = profileUrl
    )
