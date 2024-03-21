package com.goms.goms_android_v2

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goms.common.result.Result
import com.goms.common.result.asResult
import com.goms.data.repository.account.AccountRepository
import com.goms.data.repository.setting.SettingRepository
import com.goms.datastore.AuthTokenDataSource
import com.goms.domain.notification.SaveDeviceTokenUseCase
import com.goms.model.response.account.ProfileResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val saveDeviceTokenUseCase: SaveDeviceTokenUseCase,
    private val authTokenDataSource: AuthTokenDataSource,
    private val settingRepository: SettingRepository
) : ViewModel() {
    private val _themeState = MutableStateFlow("")
    val themeState = _themeState.asStateFlow()

    val uiState: StateFlow<MainActivityUiState> = flow {
        accountRepository.getProfile().collect { profileResponse ->
            emit(profileResponse)
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

    private val _saveDeviceTokenUiState = MutableStateFlow<Result<Unit>>(Result.Loading)
    val saveDeviceTokenUiState = _saveDeviceTokenUiState.asStateFlow()

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

    fun setAuthority(authority: String) = viewModelScope.launch {
        authTokenDataSource.setAuthority(authority = authority)
    }

    fun deleteToken() = viewModelScope.launch {
        authTokenDataSource.removeAccessToken()
        authTokenDataSource.removeRefreshToken()
        authTokenDataSource.removeAccessTokenExp()
        authTokenDataSource.removeRefreshTokenExp()
        authTokenDataSource.removeAuthority()
    }

    fun getSettingInfo() = viewModelScope.launch {
        val themeValue = settingRepository.getThemeValue().first().replace("\"","")
        _themeState.value = themeValue
    }
}

sealed interface MainActivityUiState {
    object Loading : MainActivityUiState
    data class Success(val getProfileResponse: ProfileResponse) : MainActivityUiState
    data class Error(val exception: Throwable) : MainActivityUiState
}