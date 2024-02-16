package com.goms.main

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.goms.common.result.Result
import com.goms.design_system.component.bottomsheet.AdminSelectorBottomSheet
import com.goms.design_system.component.bottomsheet.MultipleSelectorBottomSheet
import com.goms.design_system.component.button.GomsBackButton
import com.goms.design_system.component.textfield.GomsSearchTextField
import com.goms.design_system.theme.GomsTheme
import com.goms.design_system.util.keyboardAsState
import com.goms.main.component.StudentManagementList
import com.goms.main.component.StudentManagementText
import com.goms.main.viewmodel.GetStudentListUiState
import com.goms.main.viewmodel.MainViewModelProvider
import com.goms.main.viewmodel.StudentSearchUiState
import com.goms.model.enum.Gender
import com.goms.model.enum.Grade
import com.goms.model.enum.Major
import com.goms.model.enum.Status
import com.goms.model.request.council.AuthorityRequest
import java.util.UUID

@Composable
fun StudentManagementRoute(
    viewModelStoreOwner: ViewModelStoreOwner,
    onBackClick: () -> Unit
) {
    MainViewModelProvider(viewModelStoreOwner = viewModelStoreOwner) { viewModel ->
        val studentSearch by viewModel.studentSearch.collectAsStateWithLifecycle()
        val status by viewModel.status.collectAsStateWithLifecycle()
        val filterStatus by viewModel.filterStatus.collectAsStateWithLifecycle()
        val filterGrade by viewModel.filterGrade.collectAsStateWithLifecycle()
        val filterGender by viewModel.filterGender.collectAsStateWithLifecycle()
        val filterMajor by viewModel.filterMajor.collectAsStateWithLifecycle()
        val getStudentListUiState by viewModel.getStudentListUiState.collectAsStateWithLifecycle()
        val changeAuthorityUiState by viewModel.changeAuthorityUiState.collectAsStateWithLifecycle()
        val setBlackListUiState by viewModel.setBlackListUiState.collectAsStateWithLifecycle()
        val deleteBlackListUiState by viewModel.deleteBlackListUiState.collectAsStateWithLifecycle()
        val studentSearchUiState by viewModel.studentSearchUiState.collectAsStateWithLifecycle()

        Log.d("testt", studentSearchUiState.toString())

        when (changeAuthorityUiState) {
            is Result.Success -> {
                viewModel.getStudentList()
                viewModel.initChangeAuthority()
            }
            else -> Unit
        }

        when (setBlackListUiState) {
            is Result.Success -> {
                viewModel.getStudentList()
                viewModel.initSetBlackList()
            }
            else -> Unit
        }

        StudentManagementScreen(
            studentSearch = studentSearch,
            status = status,
            filterStatus = filterStatus,
            filterGrade = filterGrade,
            filterGender = filterGender,
            filterMajor = filterMajor,
            onStudentSearchChange = viewModel::onStudentSearchChange,
            onStatusChange = viewModel::onStatusChange,
            onFilterStatusChange = viewModel::onFilterStatusChange,
            onFilterGradeChange = viewModel::onFilterGradeChange,
            onFilterGenderChange = viewModel::onFilterGenderChange,
            onFilterMajorChange = viewModel::onFilterMajorChange,
            getStudentListUiState = getStudentListUiState,
            studentSearchUiState = studentSearchUiState,
            onBackClick = onBackClick,
            studentListCallBack = { viewModel.getStudentList() },
            studentSearchCallBack = { name ->
                viewModel.studentSearch(
                    grade = if (filterGrade.isNotBlank()) Grade.values().find { it.value == filterGrade }!!.enum else null,
                    gender = if (filterGender.isNotBlank()) Gender.values().find { it.value == filterGender }!!.name else null,
                    major = if (filterMajor.isNotBlank()) Major.values().find { it.value == filterMajor }!!.name else null,
                    name = name.ifBlank { null },
                    isBlackList = if (filterStatus.isBlank()) null
                    else filterStatus == Status.BLACK_LIST.value,
                    authority = if (filterStatus.isBlank() || filterStatus == Status.BLACK_LIST.value) null
                    else Status.values().find { it.value ==  filterStatus }!!.name
                )
            },
            changeAuthorityCallBack = { accountIdx, authority ->
                viewModel.changeAuthority(
                    body = AuthorityRequest(
                        accountIdx = accountIdx.toString(),
                        authority = Status.values().find { it.value == authority }!!.name
                    )
                )
                viewModel.deleteBlackList(accountIdx = accountIdx)
            },
            setBlackListCallBack = { accountIdx ->
                viewModel.setBlackList(accountIdx = accountIdx)
            }
        )
    }
}

@Composable
fun StudentManagementScreen(
    studentSearch: String,
    status: String,
    filterStatus: String,
    filterGrade: String,
    filterGender: String,
    filterMajor: String,
    onStudentSearchChange: (String) -> Unit,
    onStatusChange: (String) -> Unit,
    onFilterStatusChange: (String) -> Unit,
    onFilterGradeChange: (String) -> Unit,
    onFilterGenderChange: (String) -> Unit,
    onFilterMajorChange: (String) -> Unit,
    getStudentListUiState: GetStudentListUiState,
    studentSearchUiState: StudentSearchUiState,
    onBackClick: () -> Unit,
    studentListCallBack: () -> Unit,
    studentSearchCallBack: (String) -> Unit,
    changeAuthorityCallBack: (UUID, String) -> Unit,
    setBlackListCallBack: (UUID) -> Unit
) {
    LaunchedEffect(true) {
        studentListCallBack()
    }

    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current
    val isKeyboardOpen by keyboardAsState()
    var uuid by remember { mutableStateOf(UUID.randomUUID()) }
    var onStatusBottomSheetOpenClick by remember { mutableStateOf(false) }
    var onFilterBottomSheetOpenClick by remember { mutableStateOf(false) }

    LaunchedEffect(isKeyboardOpen) {
        if (!isKeyboardOpen) {
            focusManager.clearFocus()
        }
    }

    GomsTheme { colors, typography ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.BLACK)
                .statusBarsPadding()
                .navigationBarsPadding()
                .pointerInput(Unit) {
                    detectTapGestures {
                        focusManager.clearFocus()
                    }
                }
        ) {
            GomsBackButton {
                onBackClick()
            }
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                StudentManagementText(modifier = Modifier.align(Alignment.Start))
                Spacer(modifier = Modifier.height(16.dp))
                GomsSearchTextField(
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    placeHolder = "학생 검색...",
                    setText = studentSearch,
                    onValueChange = onStudentSearchChange,
                    onSearchTextChange = studentSearchCallBack,
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                StudentManagementList(
                    getStudentListUiState = getStudentListUiState,
                    studentSearchUiState = studentSearchUiState,
                    onBottomSheetOpenClick = { onFilterBottomSheetOpenClick = true },
                    onClick = { accountIdx, authority ->
                        onStatusBottomSheetOpenClick = true
                        uuid = accountIdx
                        onStatusChange(authority)
                    }
                )
            }
        }
        if (onStatusBottomSheetOpenClick) {
            AdminSelectorBottomSheet(
                modifier = Modifier.fillMaxWidth(),
                title = "유저 권한 변경",
                subTitle = "역할",
                list = listOf(
                    Status.ROLE_STUDENT.value,
                    Status.ROLE_STUDENT_COUNCIL.value,
                    Status.BLACK_LIST.value
                ),
                selected = status,
                itemChange = onStatusChange,
                closeSheet = {
                    onStatusBottomSheetOpenClick = false
                    if (status != Status.BLACK_LIST.value) {
                        changeAuthorityCallBack(uuid, status)
                    } else {
                        setBlackListCallBack(uuid)
                    }
                }
            )
        }
        if (onFilterBottomSheetOpenClick) {
            MultipleSelectorBottomSheet(
                modifier = Modifier.fillMaxWidth(),
                title = "필터",
                subTitle1 = "역할",
                list1 = listOf(
                    Status.ROLE_STUDENT.value,
                    Status.ROLE_STUDENT_COUNCIL.value,
                    Status.BLACK_LIST.value
                ),
                selected1 = filterStatus,
                itemChange1 = onFilterStatusChange,
                subTitle2 = "학년",
                list2 = listOf(
                    Grade.FIRST_GRADE.value,
                    Grade.SECOND_GRADE.value,
                    Grade.THIRD_GRADE.value
                ),
                selected2 = filterGrade,
                itemChange2 = onFilterGradeChange,
                subTitle3 = "성별",
                list3 = listOf(
                    Gender.MAN.value,
                    Gender.WOMAN.value
                ),
                selected3 = filterGender,
                itemChange3 = onFilterGenderChange,
                subTitle4 = "학과",
                list4 = listOf(
                    Major.SW_DEVELOP.value,
                    Major.SMART_IOT.value,
                    Major.AI.value
                ),
                selected4 = filterMajor,
                itemChange4 = onFilterMajorChange,
                closeSheet = {
                    onFilterBottomSheetOpenClick = false
                    studentSearchCallBack(studentSearch)
                }
            )
        }
    }
}