package com.goms.model.request.account

data class FindPasswordRequestModel(
    val email: String,
    val password: String
)
