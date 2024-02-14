package com.goms.main.viewmodel

import com.goms.model.response.late.RankResponse

sealed interface GetLateRankListUiState {
    object Loading : GetLateRankListUiState
    object Empty : GetLateRankListUiState
    data class Success(val getLateRankListResponse: List<RankResponse>) : GetLateRankListUiState
    data class Error(val exception: Throwable) : GetLateRankListUiState
}