package com.goms.main.viewmodel.uistate

import com.goms.model.response.council.LateResponseModel

sealed interface GetLateListUiState {
    object Loading : GetLateListUiState
    object Empty : GetLateListUiState
    data class Success(val getLateRankListResponse: List<LateResponseModel>) : GetLateListUiState
    data class Error(val exception: Throwable) : GetLateListUiState
}