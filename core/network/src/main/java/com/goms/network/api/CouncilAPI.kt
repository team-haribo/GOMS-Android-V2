package com.goms.network.api

import retrofit2.http.DELETE
import retrofit2.http.Path
import java.util.UUID

interface CouncilAPI {
    @DELETE("/api/v2/student-council/outing/{accountIdx}")
    suspend fun deleteOuting(
        @Path("accountIdx") accountIdx: UUID
    )
}