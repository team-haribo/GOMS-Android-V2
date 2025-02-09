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
    fun getStudentList(): List<StudentResponse>

    @PATCH(RequestUrls.COUNCIL.authority)
    fun changeAuthority(
        @Body body: AuthorityRequest
    )

    @POST(RequestUrls.COUNCIL.blackList)
    fun setBlackList(
        @Path("accountIdx") accountIdx: UUID
    )

    @POST(RequestUrls.COUNCIL.forcingouting)
    fun forcingOuting(
        @Path("outingIdx") outingIdx: UUID
    )


    @DELETE(RequestUrls.COUNCIL.blackList)
    fun deleteBlackList(
        @Path("accountIdx") accountIdx: UUID
    )

    @GET(RequestUrls.COUNCIL.search)
    fun studentSearch(
        @Query("grade") grade: Int?,
        @Query("gender") gender: String?,
        @Query("major") major: String?,
        @Query("name") name: String?,
        @Query("isBlackList") isBlackList: Boolean?,
        @Query("authority") authority: String?
    ): List<StudentResponse>

    @POST(RequestUrls.COUNCIL.outing)
    fun getOutingUUID(): OutingUUIDResponse

    @DELETE(RequestUrls.COUNCIL.deleteOuting)
    fun deleteOuting(
        @Path("accountIdx") accountIdx: UUID
    )

    @GET(RequestUrls.COUNCIL.late)
    fun getLateList(
        @Query("date") date: LocalDate
    ): List<LateResponse>
}