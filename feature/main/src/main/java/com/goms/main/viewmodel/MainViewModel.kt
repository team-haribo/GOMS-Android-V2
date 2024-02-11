package com.goms.main.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goms.common.result.Result
import com.goms.common.result.asResult
import com.goms.datastore.AuthTokenDataSource
import com.goms.domain.account.GetProfileUseCase
import com.goms.domain.council.DeleteOutingUseCase
import com.goms.domain.council.GetLateListUseCase
import com.goms.domain.late.GetLateRankListUseCase
import com.goms.domain.outing.GetOutingCountUseCase
import com.goms.domain.outing.GetOutingListUseCase
import com.goms.domain.outing.OutingSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getProfileUseCase: GetProfileUseCase,
    private val getLateRankListUseCase: GetLateRankListUseCase,
    private val getOutingListUseCase: GetOutingListUseCase,
    private val getOutingCountUseCase: GetOutingCountUseCase,
    private val outingSearchUseCase: OutingSearchUseCase,
    private val deleteOutingUseCase: DeleteOutingUseCase,
    private val getLateListUseCase: GetLateListUseCase,
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

    private val _outingSearchUiState = MutableStateFlow<OutingSearchUiState>(OutingSearchUiState.Loading)
    val outingSearchUiState = _outingSearchUiState.asStateFlow()

    private val _deleteOutingUiState = MutableStateFlow<Result<Unit>>(Result.Loading)
    val deleteOutingUiState = _deleteOutingUiState.asStateFlow()

    private val _getLateListUiState = MutableStateFlow<GetLateListUiState>(GetLateListUiState.Loading)
    val getLateListUiState = _getLateListUiState.asStateFlow()

    var outingSearch = savedStateHandle.getStateFlow(key = OUTING_SEARCH, initialValue = "")
    var studentSearch = savedStateHandle.getStateFlow(key = STUDENT_SEARCH, initialValue = "")
    var status = savedStateHandle.getStateFlow(key = STATUS, initialValue = "")
    var filterStatus = savedStateHandle.getStateFlow(key = FILTER_STATUS, initialValue = "")
    var filterGrade = savedStateHandle.getStateFlow(key = FILTER_GRADE, initialValue = "")
    var filterClass = savedStateHandle.getStateFlow(key = FILTER_CLASS, initialValue = "")


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

    fun outingSearch(name: String) = viewModelScope.launch {
            if (name.isEmpty()) {
                _outingSearchUiState.value = OutingSearchUiState.QueryEmpty
            } else {
                outingSearchUseCase(name = name)
                    .asResult()
                    .collectLatest { result ->
                        when (result) {
                            is Result.Loading -> _outingSearchUiState.value = OutingSearchUiState.Loading
                            is Result.Success -> {
                                if (result.data.isEmpty()) {
                                    _outingSearchUiState.value = OutingSearchUiState.Empty
                                } else {
                                    _outingSearchUiState.value = OutingSearchUiState.Success(result.data)
                                }
                            }
                            is Result.Error -> _outingSearchUiState.value = OutingSearchUiState.Error(result.exception)
                        }
                    }
            }
    }

    fun deleteOuting(accountIdx: UUID) = viewModelScope.launch {
        deleteOutingUseCase(accountIdx = accountIdx)
            .onSuccess {
                it.catch {  remoteError ->
                    _deleteOutingUiState.value = Result.Error(remoteError)
                }.collect { result ->
                    _deleteOutingUiState.value = Result.Success(result)
                }
            }.onFailure {
                _deleteOutingUiState.value = Result.Error(it)
            }
    }

    fun getLateList(date: LocalDate) = viewModelScope.launch {
        getLateListUseCase(date = date)
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _getLateListUiState.value = GetLateListUiState.Loading
                    is Result.Success -> {
                        if (result.data.isEmpty()) {
                            _getLateListUiState.value = GetLateListUiState.Empty
                        } else {
                            _getLateListUiState.value = GetLateListUiState.Success(result.data)
                        }
                    }
                    is Result.Error -> _getLateListUiState.value = GetLateListUiState.Error(result.exception)
                }
            }
    }

    fun onOutingSearchChange(value: String) {
        savedStateHandle[OUTING_SEARCH] = value
    }

    fun onStudentSearchChange(value: String) {
        savedStateHandle[STUDENT_SEARCH] = value
    }

    fun onStatusChange(value: String) {
        savedStateHandle[STATUS] = value
    }

    fun onFilterStatusChange(value: String) {
        savedStateHandle[FILTER_STATUS] = value
    }

    fun onFilterGradeChange(value: String) {
        savedStateHandle[FILTER_GRADE] = value
    }
    fun onFilterClassChange(value: String) {
        savedStateHandle[FILTER_CLASS] = value
    }
}

private const val OUTING_SEARCH = "outing search"
private const val STUDENT_SEARCH = "student Search"
private const val STATUS = "status"
private const val FILTER_STATUS = "filter status"
private const val FILTER_GRADE = "filter grade"
private const val FILTER_CLASS = "filter class"