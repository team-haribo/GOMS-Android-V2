package com.goms.re_password.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goms.common.network.errorHandling
import com.goms.domain.account.RePasswordUseCase
import com.goms.model.request.account.RePasswordRequestModel
import com.goms.re_password.viewmodel.uistate.RePasswordUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RePasswordViewmodel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val rePasswordUseCase: RePasswordUseCase
) : ViewModel() {
    private val _rePasswordUiState = MutableStateFlow<RePasswordUiState>(RePasswordUiState.Loading)
    val rePasswordUiState = _rePasswordUiState.asStateFlow()

    var password = savedStateHandle.getStateFlow(key = PASSWORD, initialValue = "")
    var newPassword = savedStateHandle.getStateFlow(key = NEW_PASSWORD, initialValue = "")
    var newCheckPassword = savedStateHandle.getStateFlow(key = NEW_CHECK_PASSWORD, initialValue = "")

    fun rePassword(body: RePasswordRequestModel) = viewModelScope.launch {
        rePasswordUseCase(body = body)
            .onSuccess {
                it.catch {  remoteError ->
                    _rePasswordUiState.value = RePasswordUiState.Error(remoteError)
                    remoteError.errorHandling(
                        badRequestAction = { _rePasswordUiState.value = RePasswordUiState.BadRequest },
                    )
                }.collect { result ->
                    _rePasswordUiState.value = RePasswordUiState.Success
                }
            }.onFailure {
                _rePasswordUiState.value = RePasswordUiState.Error(it)
                it.errorHandling(
                    badRequestAction = { _rePasswordUiState.value = RePasswordUiState.BadRequest },
                )
            }
    }

    fun initRePassword() {
        _rePasswordUiState.value = RePasswordUiState.Loading
    }

    fun onPasswordChange(value: String) {
        savedStateHandle[PASSWORD] = value
    }

    fun onNewPasswordChange(value: String) {
        savedStateHandle[NEW_PASSWORD] = value
    }

    fun onNewCheckPasswordChange(value: String) {
        savedStateHandle[NEW_CHECK_PASSWORD] = value
    }
}

private const val PASSWORD = "password"
private const val NEW_PASSWORD = "new password"
private const val NEW_CHECK_PASSWORD = "new check password"
