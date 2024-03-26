package com.goms.setting.viewmodel

import com.goms.model.response.account.ProfileResponseModel

sealed interface GetProfileUiState {
    object Loading : GetProfileUiState
    data class Success(val getProfileResponseModel: ProfileResponseModel) : GetProfileUiState
    data class Error(val exception: Throwable) : GetProfileUiState
}