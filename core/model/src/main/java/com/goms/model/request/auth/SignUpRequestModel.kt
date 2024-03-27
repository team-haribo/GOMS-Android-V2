package com.goms.model.request.auth

data class SignUpRequestModel(
    val email: String,
    val password: String,
    val name: String,
    val gender: String,
    val major: String
)
