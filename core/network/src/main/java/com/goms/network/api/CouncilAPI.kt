package com.goms.network.api

import com.goms.network.di.RequestUrls
import com.goms.network.dto.request.council.AuthorityRequest
import com.goms.network.dto.response.council.LateResponse
import com.goms.network.dto.response.council.OutingUUIDResponse
import com.goms.network.dto.response.council.StudentResponse
import kotlinx.datetime.LocalDate
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.UUID

interface CouncilAPI {
    @GET(RequestUrls.COUNCIL.accounts)
    suspend fun getStudentList(): List<StudentResponse>

    @PATCH(RequestUrls.COUNCIL.authority)
    suspend fun changeAuthority(
        @Body body: AuthorityRequest
    )

    @POST(RequestUrls.COUNCIL.blackList)
    suspend fun setBlackList(
        @Path("accountIdx") accountIdx: UUID
    )

    @DELETE(RequestUrls.COUNCIL.blackList)
    suspend fun deleteBlackList(
        @Path("accountIdx") accountIdx: UUID
    )

    @GET(RequestUrls.COUNCIL.search)
    suspend fun studentSearch(
        @Query("grade") grade: Int?,
        @Query("gender") gender: String?,
        @Query("major") major: String?,
        @Query("name") name: String?,
        @Query("isBlackList") isBlackList: Boolean?,
        @Query("authority") authority: String?
    ): List<StudentResponse>

    @POST(RequestUrls.COUNCIL.outing)
    suspend fun getOutingUUID(): OutingUUIDResponse

    @DELETE(RequestUrls.COUNCIL.deleteOuting)
    suspend fun deleteOuting(
        @Path("accountIdx") accountIdx: UUID
    )

    @GET(RequestUrls.COUNCIL.late)
    suspend fun getLateList(
        @Query("date") date: LocalDate
    ): List<LateResponse>
}