package com.goms.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goms.common.Event
import com.goms.common.errorHandling
import com.goms.domain.auth.LoginUseCase
import com.goms.model.request.auth.LoginRequest
import com.goms.model.response.auth.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val _loginResponse = MutableStateFlow<Event<LoginResponse>>(Event.Loading)
    val loginResponse = _loginResponse.asStateFlow()

    fun login(body: LoginRequest) = viewModelScope.launch {
        loginUseCase(
            body = body
        ).onSuccess {
            it.catch { remoteError ->
                _loginResponse.value = remoteError.errorHandling()
            }.collect { response ->
                _loginResponse.value = Event.Success(data = response)
            }
        }.onFailure {
            _loginResponse.value = it.errorHandling()
        }
    }
}