package com.goms.network.api

import com.goms.model.request.council.AuthorityRequest
import com.goms.model.response.council.LateResponse
import com.goms.model.response.council.OutingUUIDResponse
import com.goms.model.response.council.StudentResponse
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
    @GET("/api/v2/student-council/accounts")
    suspend fun getStudentList(): List<StudentResponse>

    @PATCH("/api/v2/student-council/authority")
    suspend fun changeAuthority(
        @Body body: AuthorityRequest
    )

    @POST("/api/v2/student-council/black-list/{accountIdx}")
    suspend fun setBlackList(
        @Path("accountIdx") accountIdx: UUID
    )

    @DELETE("/api/v2/student-council/black-list/{accountIdx}")
    suspend fun deleteBlackList(
        @Path("accountIdx") accountIdx: UUID
    )

    @GET("/api/v2/student-council/search")
    suspend fun studentSearch(
        @Query("grade") grade: Int?,
        @Query("gender") gender: String?,
        @Query("major") major: String?,
        @Query("name") name: String?,
        @Query("isBlackList") isBlackList: Boolean?,
        @Query("authority") authority: String?
    ): List<StudentResponse>

    @POST("/api/v2/student-council/outing")
    suspend fun getOutingUUID(): OutingUUIDResponse

    @DELETE("/api/v2/student-council/outing/{accountIdx}")
    suspend fun deleteOuting(
        @Path("accountIdx") accountIdx: UUID
    )

    @GET("/api/v2/student-council/late")
    suspend fun getLateList(
        @Query("date") date: LocalDate
    ): List<LateResponse>
}