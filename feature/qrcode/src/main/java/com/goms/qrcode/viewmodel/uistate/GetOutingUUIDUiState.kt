package com.goms.qrcode.viewmodel.uistate

import com.goms.model.response.council.OutingUUIDResponseModel

sealed interface GetOutingUUIDUiState {
    object Loading : GetOutingUUIDUiState
    data class Success(val getOutingUUIDResponseModel: OutingUUIDResponseModel) :
        GetOutingUUIDUiState
    data class Error(val exception: Throwable) : GetOutingUUIDUiState
}