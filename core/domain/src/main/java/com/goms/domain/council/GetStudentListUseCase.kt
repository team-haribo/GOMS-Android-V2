package com.goms.domain.council

import com.goms.data.repository.council.CouncilRepository
import com.goms.model.response.council.StudentResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStudentListUseCase @Inject constructor(
    private val councilRepository: CouncilRepository
) {
    suspend operator fun invoke(): Flow<List<StudentResponse>> =
        councilRepository.getStudentList()
}