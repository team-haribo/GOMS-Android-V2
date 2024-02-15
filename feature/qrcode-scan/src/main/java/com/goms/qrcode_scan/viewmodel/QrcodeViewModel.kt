package com.goms.qrcode_scan.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goms.common.result.Result
import com.goms.common.result.asResult
import com.goms.domain.auth.LoginUseCase
import com.goms.domain.auth.SaveTokenUseCase
import com.goms.domain.outing.OutingUseCase
import com.goms.model.request.auth.SignUpRequest
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
    private val outingUseCase: OutingUseCase
) : ViewModel() {
    private val _outingState = MutableStateFlow<Result<Unit>>(Result.Loading)
    val outingState = _outingState.asStateFlow()

    fun outing(outingUUID: UUID) = viewModelScope.launch {
        outingUseCase(outingUUID = outingUUID)
            .onSuccess {
                it.catch {  remoteError ->
                    _outingState.value = Result.Error(remoteError)
                }.collect { result ->
                    _outingState.value = Result.Success(result)
                }
            }.onFailure {
                _outingState.value = Result.Error(it)
            }
    }
}