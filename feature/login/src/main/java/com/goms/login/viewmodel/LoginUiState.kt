package com.goms.login.viewmodel

import com.goms.model.enum.Authority
import com.goms.model.response.auth.LoginResponse

sealed interface LoginUiState {
    object Loading : LoginUiState
    data class Success(val loginResponse: LoginResponse) : LoginUiState {
        fun isUser(): Boolean = loginResponse.authority.name == Authority.ROLE_STUDENT.name
    }
    data class Error(val exception: Throwable) : LoginUiState
}