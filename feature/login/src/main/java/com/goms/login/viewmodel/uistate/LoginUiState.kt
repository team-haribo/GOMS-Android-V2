package com.goms.login.viewmodel.uistate

import com.goms.model.enum.Authority
import com.goms.model.response.auth.LoginResponseModel

sealed interface LoginUiState {
    object Loading : LoginUiState
    data class Success(val loginResponseModel: LoginResponseModel) : LoginUiState {
        fun isUser(): Boolean = loginResponseModel.authority.name == Authority.ROLE_STUDENT.name
    }
    object BadRequest : LoginUiState
    object NotFound : LoginUiState
    object EmailNotValid : LoginUiState
    data class Error(val exception: Throwable) : LoginUiState
}