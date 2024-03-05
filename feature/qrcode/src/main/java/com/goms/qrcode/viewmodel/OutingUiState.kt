package com.goms.qrcode.viewmodel

import com.goms.model.response.account.ProfileResponse
import com.goms.model.response.council.OutingUUIDResponse

sealed interface OutingUiState {
    object Loading : OutingUiState
    object Success : OutingUiState
    object BadRequest : OutingUiState
    object Error : OutingUiState
}