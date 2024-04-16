package com.goms.qrcode.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goms.common.network.errorHandling
import com.goms.common.result.Result
import com.goms.common.result.asResult
import com.goms.data.repository.auth.AuthRepository
import com.goms.domain.account.GetProfileUseCase
import com.goms.domain.council.GetOutingUUIDUseCase
import com.goms.domain.outing.OutingUseCase
import com.goms.qrcode.viewmodel.uistate.GetOutingUUIDUiState
import com.goms.qrcode.viewmodel.uistate.GetProfileUiState
import com.goms.qrcode.viewmodel.uistate.OutingUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class QrcodeViewModel @Inject constructor(
    private val outingUseCase: OutingUseCase,
    private val getOutingUUIDUseCase: GetOutingUUIDUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    private val authRepository: AuthRepository
) : ViewModel() {
    val role = authRepository.getRole()

    private val _outingState = MutableStateFlow<OutingUiState>(OutingUiState.Loading)
    val outingState = _outingState.asStateFlow()

    private val _getOutingUUIDState = MutableStateFlow<GetOutingUUIDUiState>(GetOutingUUIDUiState.Loading)
    val getOutingUUIDState = _getOutingUUIDState.asStateFlow()

    private val _getProfileUiState = MutableStateFlow<GetProfileUiState>(GetProfileUiState.Loading)
    val getProfileUiState = _getProfileUiState.asStateFlow()

    fun outing(outingUUID: UUID) = viewModelScope.launch {
        outingUseCase(outingUUID = outingUUID)
            .onSuccess {
                it.catch {  remoteError ->
                    _outingState.value = OutingUiState.Error(remoteError)
                    remoteError.errorHandling(
                        badRequestAction = { _outingState.value = OutingUiState.BadRequest }
                    )
                }.collect { result ->
                    _outingState.value = OutingUiState.Success
                }
            }.onFailure {
                _outingState.value = OutingUiState.Error(it)
                it.errorHandling(
                    badRequestAction = { _outingState.value = OutingUiState.BadRequest }
                )
            }
    }

    fun qrcodeDataError() = viewModelScope.launch {
        _outingState.value = OutingUiState.BadRequest
    }

    fun getOutingUUID() = viewModelScope.launch {
        getOutingUUIDUseCase()
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _getOutingUUIDState.value = GetOutingUUIDUiState.Loading
                    is Result.Success -> _getOutingUUIDState.value = GetOutingUUIDUiState.Success(result.data)
                    is Result.Error -> _getOutingUUIDState.value = GetOutingUUIDUiState.Error(result.exception)
                }
            }
    }

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
}