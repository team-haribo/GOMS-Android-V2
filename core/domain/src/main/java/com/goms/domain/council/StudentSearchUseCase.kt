package com.goms.domain.council

import com.goms.data.repository.council.CouncilRepository
import com.goms.model.response.council.StudentResponseModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StudentSearchUseCase @Inject constructor(
    private val councilRepository: CouncilRepository
) {
    suspend operator fun invoke(
        grade: Int?,
        gender: String?,
        major: String?,
        name: String?,
        isBlackList: Boolean?,
        authority: String?
    ): Flow<List<StudentResponseModel>> =
        councilRepository.studentSearch(
            grade = grade,
            gender = gender,
            major = major,
            name = name,
            isBlackList = isBlackList,
            authority = authority
        )
}