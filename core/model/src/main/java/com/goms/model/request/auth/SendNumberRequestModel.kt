package com.goms.model.request.auth

import com.goms.model.enum.EmailStatus

data class SendNumberRequestModel(
    val email: String,
    val emailStatus: EmailStatus
)