package com.goms.sign_up.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goms.common.result.Result
import com.goms.domain.auth.SendNumberUseCase
import com.goms.domain.auth.SighUpUseCase
import com.goms.domain.auth.VerifyNumberUseCase
import com.goms.model.request.auth.SendNumberRequest
import com.goms.model.request.auth.SignUpRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val signUpUseCase: SighUpUseCase,
    private val sendNumberUseCase: SendNumberUseCase,
    private val verifyNumberUseCase: VerifyNumberUseCase
) : ViewModel() {
    private val _signUpResponse = MutableStateFlow<Result<Unit>>(Result.Loading)
    val signUpResponse = _signUpResponse.asStateFlow()

    private val _sendNumberResponse = MutableStateFlow<Result<Unit>>(Result.Loading)
    val sendNumberResponse = _sendNumberResponse.asStateFlow()

    private val _verifyNumberResponse = MutableStateFlow<Result<Unit>>(Result.Loading)
    val verifyNumberResponse = _verifyNumberResponse.asStateFlow()

    var name = savedStateHandle.getStateFlow(key = NAME, initialValue = "")
    var email = savedStateHandle.getStateFlow(key = EMAIL, initialValue = "")
    var gender = savedStateHandle.getStateFlow(key = GENDER, initialValue = "")
    var major = savedStateHandle.getStateFlow(key = MAJOR, initialValue = "")
    var number = savedStateHandle.getStateFlow(key = NUMBER, initialValue = "")
    var password = savedStateHandle.getStateFlow(key = PASSWORD, initialValue = "")

    fun signUp(body: SignUpRequest) = viewModelScope.launch {
        signUpUseCase(body = body)
            .onSuccess {
                it.catch {  remoteError ->
                    _signUpResponse.value = Result.Error(remoteError)
                }.collect { result ->
                    _signUpResponse.value = Result.Success(result)
                }
            }.onFailure {
                _signUpResponse.value = Result.Error(it)
            }
    }

    fun sendNumber(body: SendNumberRequest) = viewModelScope.launch {
        sendNumberUseCase(body = body)
            .onSuccess {
                it.catch {  remoteError ->
                    _sendNumberResponse.value = Result.Error(remoteError)
                }.collect { result ->
                    _sendNumberResponse.value = Result.Success(result)
                }
            }.onFailure {
                _sendNumberResponse.value = Result.Error(it)
            }
    }

    fun initSendNumber() {
        _sendNumberResponse.value = Result.Loading
    }


    fun verifyNumber(email: String, authCode: String) = viewModelScope.launch {
        verifyNumberUseCase(email = email, authCode = authCode)
            .onSuccess {
                it.catch {  remoteError ->
                    _verifyNumberResponse.value = Result.Error(remoteError)
                }.collect { result ->
                    _verifyNumberResponse.value = Result.Success(result)
                }
            }.onFailure {
                _verifyNumberResponse.value = Result.Error(it)
            }
    }

    fun initVerifyNumber() {
        _verifyNumberResponse.value = Result.Loading
    }

    fun onNameChange(value: String) {
        savedStateHandle[NAME] = value
    }

    fun onEmailChange(value: String) {
        savedStateHandle[EMAIL] = value
    }

    fun onGenderChange(value: String) {
        savedStateHandle[GENDER] = value
    }

    fun onMajorChange(value: String) {
        savedStateHandle[MAJOR] = value
    }

    fun onNumberChange(value: String) {
        savedStateHandle[NUMBER] = value
    }

    fun onPasswordChange(value: String) {
        savedStateHandle[PASSWORD] = value
    }
}

private const val NAME = "name"
private const val EMAIL = "email"
private const val GENDER = "gender"
private const val MAJOR = "major"
private const val NUMBER = "number"
private const val PASSWORD = "password"
