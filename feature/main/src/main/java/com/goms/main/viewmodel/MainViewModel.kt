package com.goms.main.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goms.common.result.Result
import com.goms.common.result.asResult
import com.goms.datastore.AuthTokenDataSource
import com.goms.domain.account.GetProfileUseCase
import com.goms.domain.late.GetLateRankListUseCase
import com.goms.domain.outing.GetOutingCountUseCase
import com.goms.domain.outing.GetOutingListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getProfileUseCase: GetProfileUseCase,
    private val getLateRankListUseCase: GetLateRankListUseCase,
    private val getOutingListUseCase: GetOutingListUseCase,
    private val getOutingCountUseCase: GetOutingCountUseCase,
    private val authTokenDataSource: AuthTokenDataSource
) : ViewModel() {
    val role = authTokenDataSource.getAuthority()

    private val _getProfileUiState = MutableStateFlow<GetProfileUiState>(GetProfileUiState.Loading)
    val getProfileUiState = _getProfileUiState.asStateFlow()

    private val _getLateRankListUiState = MutableStateFlow<GetLateRankListUiState>(GetLateRankListUiState.Loading)
    val getLateRankListUiState = _getLateRankListUiState.asStateFlow()

    private val _getOutingListUiState = MutableStateFlow<GetOutingListUiState>(GetOutingListUiState.Loading)
    val getOutingListUiState = _getOutingListUiState.asStateFlow()

    private val _getOutingCountUiState = MutableStateFlow<GetOutingCountUiState>(GetOutingCountUiState.Loading)
    val getOutingCountUiState = _getOutingCountUiState.asStateFlow()

    var outingSearch = savedStateHandle.getStateFlow(key = OUTING_SEARCH, initialValue = "")

    fun getProfile() = viewModelScope.launch {
        getProfileUseCase()
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _getProfileUiState.value = GetProfileUiState.Loading
                    is Result.Success -> _getProfileUiState.value = GetProfileUiState.Success(result.data)
                    is Result.Error -> _getProfileUiState.value = GetProfileUiState.Error(result.exception)
                }
            }
    }

    fun getLateRankList() = viewModelScope.launch {
        getLateRankListUseCase()
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _getLateRankListUiState.value = GetLateRankListUiState.Loading
                    is Result.Success -> _getLateRankListUiState.value = GetLateRankListUiState.Success(result.data)
                    is Result.Error -> _getLateRankListUiState.value = GetLateRankListUiState.Error(result.exception)
                }
            }
    }

    fun getOutingList() = viewModelScope.launch {
        getOutingListUseCase()
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _getOutingListUiState.value = GetOutingListUiState.Loading
                    is Result.Success -> _getOutingListUiState.value = GetOutingListUiState.Success(result.data)
                    is Result.Error -> _getOutingListUiState.value = GetOutingListUiState.Error(result.exception)
                }
            }
    }

    fun getOutingCount() = viewModelScope.launch {
        getOutingCountUseCase()
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _getOutingCountUiState.value = GetOutingCountUiState.Loading
                    is Result.Success -> {
                        if (result.data.outingCount == 0) {
                            _getOutingCountUiState.value = GetOutingCountUiState.Empty
                        } else {
                            _getOutingCountUiState.value = GetOutingCountUiState.Success(result.data)
                            getOutingList()
                        }
                    }
                    is Result.Error -> _getOutingCountUiState.value = GetOutingCountUiState.Error(result.exception)
                }
            }
    }

    fun onOutingSearchChange(value: String) {
        savedStateHandle[OUTING_SEARCH] = value
    }
}

private const val OUTING_SEARCH = "outing search"