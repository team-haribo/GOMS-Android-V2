package com.goms.main.viewmodel

import com.goms.model.response.council.StudentResponse

sealed interface GetStudentListUiState {
    object Loading : GetStudentListUiState
    data class Success(val getStudentResponse: List<StudentResponse>) : GetStudentListUiState
    data class Error(val exception: Throwable) : GetStudentListUiState
}