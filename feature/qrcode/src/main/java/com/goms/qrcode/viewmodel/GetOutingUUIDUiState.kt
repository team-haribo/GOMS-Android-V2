package com.goms.qrcode.viewmodel

import com.goms.model.response.account.ProfileResponse
import com.goms.model.response.council.OutingUUIDResponse

sealed interface GetOutingUUIDUiState {
    object Loading : GetOutingUUIDUiState
    data class Success(val getOutingUUIDResponse: OutingUUIDResponse) : GetOutingUUIDUiState
    data class Error(val exception: Throwable) : GetOutingUUIDUiState
}