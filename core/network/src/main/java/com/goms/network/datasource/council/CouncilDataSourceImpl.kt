package com.goms.network.datasource.council

import com.goms.model.request.council.AuthorityRequest
import com.goms.model.response.council.LateResponse
import com.goms.model.response.council.OutingUUIDResponse
import com.goms.model.response.council.StudentResponse
import com.goms.model.response.outing.OutingResponse
import com.goms.network.api.CouncilAPI
import com.goms.network.util.GomsApiHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.datetime.LocalDate
import java.util.UUID
import javax.inject.Inject

class CouncilDataSourceImpl @Inject constructor(
    private val councilAPI: CouncilAPI
) : CouncilDataSource {
    override suspend fun getStudentList(): Flow<List<StudentResponse>> = flow {
        emit(
            GomsApiHandler<List<StudentResponse>>()
                .httpRequest { councilAPI.getStudentList() }
                .sendRequest()
        )
    }.flowOn(Dispatchers.IO)

    override suspend fun changeAuthority(body: AuthorityRequest): Flow<Unit> = flow {
        emit(
            GomsApiHandler<Unit>()
                .httpRequest { councilAPI.changeAuthority(body = body) }
                .sendRequest()
        )
    }.flowOn(Dispatchers.IO)

    override suspend fun setBlackList(accountIdx: UUID): Flow<Unit> = flow {
        emit(
            GomsApiHandler<Unit>()
                .httpRequest { councilAPI.setBlackList(accountIdx = accountIdx) }
                .sendRequest()
        )
    }.flowOn(Dispatchers.IO)

    override suspend fun deleteBlackList(accountIdx: UUID): Flow<Unit> = flow {
        emit(
            GomsApiHandler<Unit>()
                .httpRequest { councilAPI.deleteBlackList(accountIdx = accountIdx) }
                .sendRequest()
        )
    }.flowOn(Dispatchers.IO)

    override suspend fun studentSearch(
        grade: Int?,
        gender: String?,
        major: String?,
        name: String?,
        isBlackList: Boolean?,
        authority: String?
    ): Flow<List<StudentResponse>> = flow {
        emit(
            GomsApiHandler<List<StudentResponse>>()
                .httpRequest {
                    councilAPI.studentSearch(
                        grade = grade,
                        gender = gender,
                        major = major,
                        name = name,
                        isBlackList = isBlackList,
                        authority = authority
                    )
                }
                .sendRequest()
        )
    }.flowOn(Dispatchers.IO)

    override suspend fun getOutingUUID(): Flow<OutingUUIDResponse> = flow {
        emit(
            GomsApiHandler<OutingUUIDResponse>()
                .httpRequest { councilAPI.getOutingUUID() }
                .sendRequest()
        )
    }.flowOn(Dispatchers.IO)

    override suspend fun deleteOuting(accountIdx: UUID): Flow<Unit> = flow {
        emit(
            GomsApiHandler<Unit>()
                .httpRequest { councilAPI.deleteOuting(accountIdx = accountIdx) }
                .sendRequest()
        )
    }.flowOn(Dispatchers.IO)

    override suspend fun getLateList(date: LocalDate): Flow<List<LateResponse>> = flow {
        emit(
            GomsApiHandler<List<LateResponse>>()
                .httpRequest { councilAPI.getLateList(date = date) }
                .sendRequest()
        )
    }.flowOn(Dispatchers.IO)
}