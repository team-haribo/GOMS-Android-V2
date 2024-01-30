package com.goms.main.viewmodel

import com.goms.model.response.account.ProfileResponse

sealed interface GetProfileUiState {
    object Loading : GetProfileUiState
    data class Success(val getProfileResponse: ProfileResponse) : GetProfileUiState
    data class Error(val exception: Throwable) : GetProfileUiState
}