package com.goms.main.viewmodel

import com.goms.model.response.council.LateResponse

sealed interface GetLateListUiState {
    object Loading : GetLateListUiState
    object Empty : GetLateListUiState
    data class Success(val getLateRankListResponse: List<LateResponse>) : GetLateListUiState
    data class Error(val exception: Throwable) : GetLateListUiState
}