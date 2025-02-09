package com.goms.domain.council

import com.goms.data.repository.council.CouncilRepository
import com.goms.model.response.council.StudentResponseModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStudentListUseCase @Inject constructor(
    private val councilRepository: CouncilRepository
) {
    operator fun invoke(): Flow<List<StudentResponseModel>> =
        councilRepository.getStudentList()
}