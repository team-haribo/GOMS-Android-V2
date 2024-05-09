package com.goms.goms_android_v2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goms.common.result.Result
import com.goms.common.result.asResult
import com.goms.data.repository.account.AccountRepository
import com.goms.data.repository.auth.AuthRepository
import com.goms.data.repository.setting.SettingRepository
import com.goms.domain.auth.SaveTokenUseCase
import com.goms.domain.auth.TokenRefreshUseCase
import com.goms.domain.notification.DeleteDeviceTokenUseCase
import com.goms.domain.notification.SaveDeviceTokenUseCase
import com.goms.model.response.account.ProfileResponseModel
import com.goms.model.response.auth.LoginResponseModel
import com.goms.model.util.ResourceKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val tokenRefreshUseCase: TokenRefreshUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
    private val saveDeviceTokenUseCase: SaveDeviceTokenUseCase,
    private val deleteDeviceTokenUseCase: DeleteDeviceTokenUseCase,
    private val authRepository: AuthRepository,
    private val settingRepository: SettingRepository
) : ViewModel() {
    private val refreshToken = runBlocking { authRepository.getRefreshToken().first()  }

    val uiState: StateFlow<MainActivityUiState> = flow {
        tokenRefreshUseCase(refreshToken = "${ResourceKeys.BEARER} $refreshToken").collect {
            emit(it)
        }
    }
        .asResult()
        .map { result ->
            when (result) {
                Result.Loading -> MainActivityUiState.Loading
                is Result.Success -> MainActivityUiState.Success(result.data)
                is Result.Error -> MainActivityUiState.Error(result.exception)
            }
        }
        .stateIn(
            scope = viewModelScope,
            initialValue = MainActivityUiState.Loading,
            started = SharingStarted.WhileSubscribed(5_000),
        )

    fun saveToken(token: LoginResponseModel) = viewModelScope.launch {
        runBlocking { saveTokenUseCase(token = token) }
    }

    private val _saveDeviceTokenUiState = MutableStateFlow<Result<Unit>>(Result.Loading)
    val saveDeviceTokenUiState = _saveDeviceTokenUiState.asStateFlow()

    private val _themeState = MutableStateFlow("")
    val themeState = _themeState.asStateFlow()

    private val _qrcodeState = MutableStateFlow("")
    val qrcodeState = _qrcodeState.asStateFlow()

    private val _alarmState = MutableStateFlow("")
    val alarmState = _alarmState.asStateFlow()

    fun saveDeviceToken(deviceToken: String) = viewModelScope.launch {
        saveDeviceTokenUseCase(deviceToken = deviceToken)
            .onSuccess {
                it.catch { remoteError ->
                    _saveDeviceTokenUiState.value = Result.Error(remoteError)
                }.collect { result ->
                    _saveDeviceTokenUiState.value = Result.Success(result)
                }
            }.onFailure {
                _saveDeviceTokenUiState.value = Result.Error(it)
            }
    }

    fun deleteDeviceToken() = viewModelScope.launch {
        deleteDeviceTokenUseCase()
    }

    fun deleteToken() = viewModelScope.launch {
        authRepository.deleteToken()
    }

    fun getTheme() = viewModelScope.launch {
        val themeValue = settingRepository.getThemeValue().first().replace("\"","")
        _themeState.value = themeValue
    }

    fun getSettingInfo() = viewModelScope.launch {
        val themeValue = settingRepository.getThemeValue().first().replace("\"","")
        val qrcodeValue = settingRepository.getQrcodeValue().first().replace("\"","")
        val alarmValue = settingRepository.getAlarmValue().first().replace("\"","")
        _themeState.value = themeValue
        _qrcodeState.value = qrcodeValue
        _alarmState.value = alarmValue
    }
}

sealed interface MainActivityUiState {
    object Loading : MainActivityUiState
    data class Success(val loginResponseModel: LoginResponseModel) : MainActivityUiState
    data class Error(val exception: Throwable) : MainActivityUiState
}