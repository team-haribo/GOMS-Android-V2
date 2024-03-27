package com.goms.main.data

import androidx.compose.runtime.Immutable
import com.goms.model.enum.Gender
import com.goms.model.enum.Major
import com.goms.model.response.late.RankResponseModel

@Immutable
data class RankData(
    val accountIdx: String,
    val name: String,
    val grade: Int,
    val major: Major,
    val gender: Gender,
    val profileUrl: String?
)

fun RankResponseModel.toData() =
    RankData(
        accountIdx = accountIdx,
        name = name,
        grade = grade,
        major = major,
        gender = gender,
        profileUrl = profileUrl
    )
