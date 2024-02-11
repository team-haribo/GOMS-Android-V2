package com.goms.network.api

import com.goms.model.response.council.LateResponse
import com.goms.model.response.council.StudentResponse
import kotlinx.datetime.LocalDate
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.UUID

interface CouncilAPI {
    @GET("/api/v2/student-council/accounts")
    suspend fun getStudentList(): List<StudentResponse>

    @DELETE("/api/v2/student-council/outing/{accountIdx}")
    suspend fun deleteOuting(
        @Path("accountIdx") accountIdx: UUID
    )

    @GET("/api/v2/student-council/late")
    suspend fun getLateList(
        @Query("date") date: LocalDate
    ): List<LateResponse>
}