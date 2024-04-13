package com.goms.main.viewmodel.uistate

import com.goms.model.response.outing.OutingResponseModel

sealed interface GetOutingListUiState {
    object Loading : GetOutingListUiState
    data class Success(val getOutingListResponse: List<OutingResponseModel>) : GetOutingListUiState
    data class Error(val exception: Throwable) : GetOutingListUiState
}