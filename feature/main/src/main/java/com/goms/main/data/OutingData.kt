package com.goms.main.data

import androidx.compose.runtime.Immutable
import com.goms.model.enum.Gender
import com.goms.model.enum.Major
import com.goms.model.response.outing.OutingResponse

@Immutable
data class OutingData(
    val accountIdx: String,
    val name: String,
    val major: Major,
    val grade: Int,
    val gender: Gender,
    val profileUrl: String?,
    val createdTime: String
)

fun OutingResponse.toData() =
    OutingData(
        accountIdx = accountIdx,
        name = name,
        major = major,
        grade = grade,
        gender = gender,
        profileUrl = profileUrl,
        createdTime = createdTime
    )
