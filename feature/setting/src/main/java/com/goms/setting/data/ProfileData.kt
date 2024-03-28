package com.goms.setting.data

import androidx.compose.runtime.Immutable
import com.goms.model.enum.Authority
import com.goms.model.enum.Gender
import com.goms.model.enum.Major
import com.goms.model.response.account.ProfileResponseModel

@Immutable
data class ProfileData(
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

fun ProfileResponseModel.toData() =
    ProfileData(
        name = name,
        grade = grade,
        major = major,
        gender = gender,
        authority = authority,
        profileUrl = profileUrl,
        lateCount = lateCount,
        isOuting = isOuting,
        isBlackList = isBlackList
    )
