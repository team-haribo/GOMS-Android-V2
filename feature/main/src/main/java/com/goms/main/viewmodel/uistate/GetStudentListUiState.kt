package com.goms.main.viewmodel.uistate

import com.goms.model.response.council.StudentResponseModel

sealed interface GetStudentListUiState {
    object Loading : GetStudentListUiState
    data class Success(val getStudentResponseModel: List<StudentResponseModel>) :
        GetStudentListUiState
    data class Error(val exception: Throwable) : GetStudentListUiState
}