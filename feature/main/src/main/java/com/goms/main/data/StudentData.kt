package com.goms.main.data

import androidx.compose.runtime.Immutable
import com.goms.model.enum.Authority
import com.goms.model.enum.Gender
import com.goms.model.enum.Major
import com.goms.model.response.council.StudentResponseModel

@Immutable
data class StudentData(
    val accountIdx: String,
    val name: String,
    val grade: Int,
    val gender: Gender,
    val major: Major,
    val profileUrl: String?,
    val authority: Authority,
    val isBlackList: Boolean
)

fun StudentResponseModel.toData() =
    StudentData(
        accountIdx = accountIdx,
        name = name,
        grade = grade,
        gender = gender,
        major = major,
        profileUrl = profileUrl,
        authority = authority,
        isBlackList = isBlackList
    )
