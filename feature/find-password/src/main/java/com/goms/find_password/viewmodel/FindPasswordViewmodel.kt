package com.goms.find_password.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goms.common.network.errorHandling
import com.goms.domain.account.FindPasswordUseCase
import com.goms.domain.auth.SendNumberUseCase
import com.goms.domain.auth.VerifyNumberUseCase
import com.goms.model.request.account.FindPasswordRequestModel
import com.goms.model.request.auth.SendNumberRequestModel
import com.goms.find_password.viewmodel.uistate.FindPasswordUiState
import com.goms.find_password.viewmodel.uistate.SendNumberUiState
import com.goms.find_password.viewmodel.uistate.VerifyNumberUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FindPasswordViewmodel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val findPasswordUseCase: FindPasswordUseCase,
    private val sendNumberUseCase: SendNumberUseCase,
    private val verifyNumberUseCase: VerifyNumberUseCase
) : ViewModel() {
    private val _findPasswordUiState = MutableStateFlow<FindPasswordUiState>(FindPasswordUiState.Loading)
    val findPasswordUiState = _findPasswordUiState.asStateFlow()

    private val _sendNumberUiState = MutableStateFlow<SendNumberUiState>(SendNumberUiState.Loading)
    val sendNumberUiState = _sendNumberUiState.asStateFlow()

    private val _verifyNumberUiState = MutableStateFlow<VerifyNumberUiState>(VerifyNumberUiState.Loading)
    val verifyNumberUiState = _verifyNumberUiState.asStateFlow()

    var email = savedStateHandle.getStateFlow(key = EMAIL, initialValue = "")
    var password = savedStateHandle.getStateFlow(key = PASSWORD, initialValue = "")
    var passwordCheck = savedStateHandle.getStateFlow(key = CHECK_PASSWORD, initialValue = "")
    var number = savedStateHandle.getStateFlow(key = NUMBER, initialValue = "")

    fun findPassword(body: FindPasswordRequestModel) = viewModelScope.launch {
        findPasswordUseCase(body = body)
            .onSuccess {
                it.catch {  remoteError ->
                    _findPasswordUiState.value = FindPasswordUiState.Error(remoteError)
                    remoteError.errorHandling(
                        badRequestAction = { _findPasswordUiState.value = FindPasswordUiState.BadRequest },
                    )
                }.collect { result ->
                    _findPasswordUiState.value = FindPasswordUiState.Success
                }
            }.onFailure {
                _findPasswordUiState.value = FindPasswordUiState.Error(it)
                it.errorHandling(
                    badRequestAction = { _findPasswordUiState.value = FindPasswordUiState.BadRequest },
                )
            }
    }

    fun sendNumber(body: SendNumberRequestModel) = viewModelScope.launch {
        sendNumberUseCase(body = body)
            .onSuccess {
                it.catch {  remoteError ->
                    _sendNumberUiState.value = SendNumberUiState.Error(remoteError)
                }.collect { result ->
                    _sendNumberUiState.value = SendNumberUiState.Success
                }
            }.onFailure {
                _sendNumberUiState.value = SendNumberUiState.Error(it)
            }
    }

    fun initSendNumber() {
        _sendNumberUiState.value = SendNumberUiState.Loading
    }

    fun verifyNumber(email: String, authCode: String) = viewModelScope.launch {
        verifyNumberUseCase(email = email, authCode = authCode)
            .onSuccess {
                it.catch {  remoteError ->
                    _verifyNumberUiState.value = VerifyNumberUiState.Error(remoteError)
                    remoteError.errorHandling(
                        badRequestAction = { _verifyNumberUiState.value = VerifyNumberUiState.BadRequest },
                        notFoundAction = { _verifyNumberUiState.value = VerifyNumberUiState.NotFound }
                    )
                }.collect { result ->
                    _verifyNumberUiState.value = VerifyNumberUiState.Success
                }
            }.onFailure {
                _verifyNumberUiState.value = VerifyNumberUiState.Error(it)
                it.errorHandling(
                    badRequestAction = { _verifyNumberUiState.value = VerifyNumberUiState.BadRequest },
                    notFoundAction = { _verifyNumberUiState.value = VerifyNumberUiState.NotFound }
                )
            }
    }

    fun initVerifyNumber() {
        _verifyNumberUiState.value = VerifyNumberUiState.Loading
    }

    fun onEmailChange(value: String) {
        savedStateHandle[EMAIL] = value
    }

    fun onNumberChange(value: String) {
        savedStateHandle[NUMBER] = value
    }

    fun onPasswordChange(value: String) {
        savedStateHandle[PASSWORD] = value
    }

    fun onCheckPasswordChange(value: String) {
        savedStateHandle[CHECK_PASSWORD] = value
    }
}

private const val EMAIL = "email"
private const val PASSWORD = "password"
private const val CHECK_PASSWORD = "check password"
private const val NUMBER = "number"