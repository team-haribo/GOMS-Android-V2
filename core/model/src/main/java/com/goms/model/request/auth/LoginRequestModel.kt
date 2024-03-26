package com.goms.model.request.auth

data class LoginRequestModel(
    val email: String,
    val password: String
)