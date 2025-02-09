package com.goms.main


import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.goms.common.result.Result
import com.goms.design_system.component.bottomsheet.ListData
import com.goms.design_system.component.bottomsheet.MultipleSelectorBottomSheet
import com.goms.design_system.component.dialog.GomsTwoButtonDialog
import com.goms.design_system.component.spacer.GomsSpacer
import com.goms.design_system.component.spacer.SpacerSize
import com.goms.design_system.component.textfield.GomsSearchTextField
import com.goms.design_system.theme.GomsTheme
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.theme.ThemeType
import com.goms.design_system.util.ThemeDevicePreviews
import com.goms.design_system.util.keyboardAsState
import com.goms.main.component.StudentManagementList
import com.goms.main.component.StudentManagementText
import com.goms.main.viewmodel.MainViewModel
import com.goms.main.viewmodel.uistate.GetStudentListUiState
import com.goms.main.viewmodel.uistate.StudentSearchUiState
import com.goms.model.enum.Authority
import com.goms.model.enum.BlackList
import com.goms.model.enum.Gender
import com.goms.model.enum.Grade
import com.goms.model.enum.Major
import com.goms.model.enum.OutingState
import com.goms.model.enum.Status
import com.goms.model.request.council.AuthorityRequestModel
import com.goms.model.util.ResourceKeys
import com.goms.ui.GomsRoleBackButton
import com.goms.ui.SwitchSelectorBottomSheet
import com.goms.ui.TrackScreenViewEvent
import kotlinx.collections.immutable.toPersistentList
import java.util.UUID

@Composable
internal fun StudentManagementRoute(
    onBackClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
    viewModel: MainViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
) {
    val role by viewModel.role.collectAsStateWithLifecycle(initialValue = ResourceKeys.EMPTY)
    val studentSearch by viewModel.studentSearch.collectAsStateWithLifecycle()
    val outingState by viewModel.outingState.collectAsStateWithLifecycle()
    val airingState by viewModel.airingState.collectAsStateWithLifecycle()
    val roleState by viewModel.roleState.collectAsStateWithLifecycle()
    val filterStatus by viewModel.filterStatus.collectAsStateWithLifecycle()
    val filterGrade by viewModel.filterGrade.collectAsStateWithLifecycle()
    val filterGender by viewModel.filterGender.collectAsStateWithLifecycle()
    val filterMajor by viewModel.filterMajor.collectAsStateWithLifecycle()
    val forceOutingUiState by viewModel.forceOutingUiState.collectAsStateWithLifecycle()
    val getStudentListUiState by viewModel.getStudentListUiState.collectAsStateWithLifecycle()
    val changeAuthorityUiState by viewModel.changeAuthorityUiState.collectAsStateWithLifecycle()
    val setBlackListUiState by viewModel.setBlackListUiState.collectAsStateWithLifecycle()
    val deleteBlackListUiState by viewModel.deleteBlackListUiState.collectAsStateWithLifecycle()
    val studentSearchUiState by viewModel.studentSearchUiState.collectAsStateWithLifecycle()

    when (changeAuthorityUiState) {
        is Result.Loading -> Unit
        is Result.Success -> {
            viewModel.initChangeAuthority()
        }

        is Result.Error -> {
            onErrorToast((changeAuthorityUiState as Result.Error).exception, R.string.error_change_authority)
        }
    }

    when (forceOutingUiState) {
        is Result.Loading -> Unit
        is Result.Success -> {
            viewModel.getOutingCount()
            viewModel.initPostOuting()
        }
        is Result.Error -> {
            onErrorToast((setBlackListUiState as Result.Error).exception, R.string.error_change_forceouting)
        }
    }


    when (setBlackListUiState) {
        is Result.Loading -> Unit
        is Result.Success -> {
            viewModel.getStudentList()
            viewModel.initSetBlackList()
        }

        is Result.Error -> {
            onErrorToast((setBlackListUiState as Result.Error).exception, R.string.error_set_blacklist)
        }
    }

    when (deleteBlackListUiState) {
        is Result.Loading -> Unit
        is Result.Success -> {
            viewModel.getStudentList()
            viewModel.initDeleteBlackList()
        }

        is Result.Error -> {
            onErrorToast((setBlackListUiState as Result.Error).exception, R.string.error_delete_blacklist)
        }
    }

    StudentManagementScreen(
        role = if (role.isNotBlank()) Authority.valueOf(role) else Authority.ROLE_STUDENT,
        studentSearch = studentSearch,
        outingState = outingState,
        airingState = airingState ,
        PostOutingCallBack = viewModel::forceOuting,
        roleState = roleState,
        filterStatus = filterStatus,
        filterGrade = filterGrade,
        filterGender = filterGender,
        filterMajor = filterMajor,
        onStudentSearchChange = viewModel::onStudentSearchChange,
        onOutingStateChange = viewModel::onOutingStateChange,
        onForceOutingStateChange = viewModel::forceOutingStateChange,
        onRoleStateChange = viewModel::onRoleStateChange,
        onFilterStatusChange = viewModel::onFilterStatusChange,
        onFilterGradeChange = viewModel::onFilterGradeChange,
        onFilterGenderChange = viewModel::onFilterGenderChange,
        onFilterMajorChange = viewModel::onFilterMajorChange,
        getStudentListUiState = getStudentListUiState,
        studentSearchUiState = studentSearchUiState,
        onBackClick = onBackClick,
        onErrorToast = onErrorToast,
        studentListCallBack = viewModel::getStudentList,
        studentSearchCallBack = { name ->
            viewModel.studentSearch(
                grade = if (filterGrade.isNotBlank()) Grade.values()
                    .find { it.value == filterGrade }!!.enum else null,
                gender = if (filterGender.isNotBlank()) Gender.values()
                    .find { it.value == filterGender }!!.name else null,
                major = if (filterMajor.isNotBlank()) Major.values()
                    .find { it.value == filterMajor }!!.name else null,
                name = name.ifBlank { null },
                isBlackList = if (filterStatus.isBlank()) null
                else filterStatus == Status.BLACK_LIST.value,
                authority = if (filterStatus.isBlank() || filterStatus == Status.BLACK_LIST.value) null
                else Status.values().find { it.value == filterStatus }!!.name
            )
        },
        changeAuthorityCallBack = { accountIdx, authority ->
            viewModel.changeAuthority(
                body = AuthorityRequestModel(
                    accountIdx = accountIdx.toString(),
                    authority = authority
                )
            )
        },
        setBlackListCallBack = viewModel::setBlackList,
        deleteBlackListCallBack = viewModel::deleteBlackList,


    )
}

@Composable
private fun StudentManagementScreen(
    role: Authority,
    studentSearch: String,
    outingState: String,
    airingState: String,
    roleState: String,
    filterStatus: String,
    filterGrade: String,
    filterGender: String,
    PostOutingCallBack: (UUID) -> Unit,
    filterMajor: String,
    onStudentSearchChange: (String) -> Unit,
    onOutingStateChange: (String) -> Unit,
    onRoleStateChange: (String) -> Unit,
    onForceOutingStateChange: (String) -> Unit,
    onFilterStatusChange: (String) -> Unit,
    onFilterGradeChange: (String) -> Unit,
    onFilterGenderChange: (String) -> Unit,
    onFilterMajorChange: (String) -> Unit,
    getStudentListUiState: GetStudentListUiState,
    studentSearchUiState: StudentSearchUiState,
    onBackClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
    studentListCallBack: () -> Unit,
    studentSearchCallBack: (String) -> Unit,
    changeAuthorityCallBack: (UUID, String) -> Unit,
    setBlackListCallBack: (UUID) -> Unit,
    deleteBlackListCallBack: (UUID) -> Unit
) {
    LaunchedEffect(true) {
        studentListCallBack()
    }

    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current
    val isKeyboardOpen by keyboardAsState()
    var openDialog by remember { mutableStateOf(false) }
    var uuid by remember { mutableStateOf(UUID.randomUUID()) }
    var onStatusBottomSheetOpenClick by remember { mutableStateOf(false) }
    var onFilterBottomSheetOpenClick by remember { mutableStateOf(false) }

    LaunchedEffect(isKeyboardOpen) {
        if (!isKeyboardOpen) {
            focusManager.clearFocus()
        }
    }
        if (openDialog) {
            GomsTwoButtonDialog(
                openDialog = openDialog,
                onStateChange = { openDialog = it },
                title = stringResource(id = R.string.title_outing),
                content = if (outingState == BlackList.NO_BLACK_LIST.name) stringResource(id = R.string.want_forced_outing) else stringResource(id = R.string.force_outing_noting),
                dismissText = stringResource(id = R.string.cancel),
                checkText = stringResource(id = R.string.return_force_outing),
                onDismissClick = { openDialog = false },
                onCheckClick = {
                    PostOutingCallBack(uuid)
                    openDialog = false
                }
            )
        }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.BACKGROUND)
            .statusBarsPadding()
            .navigationBarsPadding()
            .pointerInput(Unit) {
                detectTapGestures {
                    focusManager.clearFocus()
                }
            }
    ) {
        GomsRoleBackButton(role = role) {
            onBackClick()
        }
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            StudentManagementText(modifier = Modifier.align(Alignment.Start))
            GomsSpacer(size = SpacerSize.Small)
            GomsSearchTextField(
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                placeHolder = stringResource(id = R.string.student_search),
                setText = studentSearch,
                onValueChange = onStudentSearchChange,
                onSearchTextChange = studentSearchCallBack,
                singleLine = true
            )
            GomsSpacer(size = SpacerSize.ExtraSmall)
            StudentManagementList(
                getStudentListUiState = getStudentListUiState,
                studentSearchUiState = studentSearchUiState,
                onBottomSheetOpenClick = { onFilterBottomSheetOpenClick = true },
                onErrorToast = onErrorToast,
                onClick = { accountIdx, outing, role, isOuting->
                    onStatusBottomSheetOpenClick = true
                    uuid = accountIdx
                    onForceOutingStateChange(isOuting)
                    onOutingStateChange(outing)
                    onRoleStateChange(role)
                }
            )
        }
    }
    if (onStatusBottomSheetOpenClick) {
        SwitchSelectorBottomSheet(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(id = R.string.change_user_authority),
            outing = outingState,
            airing = airingState,
            role = roleState,
            outingIdx = uuid,
            closeSheet = { outingState, roleState, airingState->
                onStatusBottomSheetOpenClick = false
                changeAuthorityCallBack(uuid, roleState)
                if (airingState== OutingState.GO_OUTING.name) {
                    deleteBlackListCallBack(uuid)
                }
                if (outingState == BlackList.BLACK_LIST.name) {
                    setBlackListCallBack(uuid)
                } else {
                    deleteBlackListCallBack(uuid)
                }

            },

            onClick = {
                selectUuid->
                uuid = selectUuid
                openDialog = true
            }

        )
    }
    if (onFilterBottomSheetOpenClick) {
        MultipleSelectorBottomSheet(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(id = R.string.filter),
            subTitles = listOf(
                stringResource(id = R.string.role),
                stringResource(id = R.string.grade),
                stringResource(id = R.string.gender),
                stringResource(id = R.string.major)
            ).toPersistentList(),
            lists = ListData(
                list = listOf(
                    listOf(
                        Status.ROLE_STUDENT.value,
                        Status.ROLE_STUDENT_COUNCIL.value,
                        Status.BLACK_LIST.value
                    ).toPersistentList(),
                    listOf(
                        Grade.FIRST_GRADE.value,
                        Grade.SECOND_GRADE.value,
                        Grade.THIRD_GRADE.value
                    ).toPersistentList(),
                    listOf(
                        Gender.MAN.value,
                        Gender.WOMAN.value
                    ).toPersistentList(),
                    listOf(
                        Major.SW_DEVELOP.value,
                        Major.SMART_IOT.value,
                        Major.AI.value
                    ).toPersistentList()
                ).toPersistentList()
            ),
            selectedItems = listOf(
                filterStatus,
                filterGrade,
                filterGender,
                filterMajor
            ).toPersistentList(),
            itemChanges = listOf(
                onFilterStatusChange,
                onFilterGradeChange,
                onFilterGenderChange,
                onFilterMajorChange
            ).toPersistentList(),
            initClick = {
                onFilterStatusChange(ResourceKeys.EMPTY)
                onFilterGradeChange(ResourceKeys.EMPTY)
                onFilterGenderChange(ResourceKeys.EMPTY)
                onFilterMajorChange(ResourceKeys.EMPTY)
            },
            closeSheet = {
                onFilterBottomSheetOpenClick = false
                studentSearchCallBack(studentSearch)
            }
        )
    }
    TrackScreenViewEvent(screenName = stringResource(id = R.string.student_management_screen))
}

@ThemeDevicePreviews
@Composable
private fun StudentManagementScreenPreview() {
    GomsTheme(ThemeType.SYSTEM.value) {
        StudentManagementScreen(
            role = Authority.ROLE_STUDENT_COUNCIL,
            studentSearch = "GOMS",
            outingState = "GOMS",
            roleState = "GOMS",
            filterStatus = "GOMS",
            filterGrade = "GOMS",
            filterGender = "GOMS",
            filterMajor = "GOMS",
            onStudentSearchChange = {},
            onOutingStateChange = {},
            onRoleStateChange = {},
            onFilterStatusChange = {},
            onFilterGradeChange = {},
            onFilterGenderChange = {},
            onFilterMajorChange = {},
            getStudentListUiState = GetStudentListUiState.Loading,
            studentSearchUiState = StudentSearchUiState.Loading,
            onBackClick = {},
            onErrorToast = { _, _ -> },
            studentListCallBack = {},
            studentSearchCallBack = {},
            changeAuthorityCallBack = { _, _ -> },
            setBlackListCallBack = {},
            PostOutingCallBack = {},
            airingState = "GOMS",
            onForceOutingStateChange = {}

        ) {}
    }
}