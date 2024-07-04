package com.goms.main

import android.content.res.Configuration
import androidx.activity.ComponentActivity
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.goms.common.result.Result
import com.goms.design_system.component.dialog.GomsTwoButtonDialog
import com.goms.design_system.component.spacer.GomsSpacer
import com.goms.design_system.component.spacer.SpacerSize
import com.goms.design_system.component.textfield.GomsSearchTextField
import com.goms.design_system.theme.GomsTheme
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.theme.ThemeType
import com.goms.design_system.util.keyboardAsState
import com.goms.main.component.OutingStatusList
import com.goms.main.component.OutingStatusText
import com.goms.main.viewmodel.uistate.GetOutingCountUiState
import com.goms.main.viewmodel.uistate.GetOutingListUiState
import com.goms.main.viewmodel.MainViewModel
import com.goms.main.viewmodel.uistate.GetLateRankListUiState
import com.goms.main.viewmodel.uistate.GetProfileUiState
import com.goms.main.viewmodel.uistate.OutingSearchUiState
import com.goms.main.viewmodel.uistate.SaveTokenUiState
import com.goms.main.viewmodel.uistate.TokenRefreshUiState
import com.goms.model.enum.Authority
import com.goms.ui.GomsRoleBackButton
import java.util.UUID

@Composable
internal fun OutingStatusRoute(
    onBackClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
    viewModel: MainViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
) {
    val role by viewModel.role.collectAsStateWithLifecycle(initialValue = "")
    val outingSearch by viewModel.outingSearch.collectAsStateWithLifecycle()
    val getOutingListUiState by viewModel.getOutingListUiState.collectAsStateWithLifecycle()
    val getOutingCountUiState by viewModel.getOutingCountUiState.collectAsStateWithLifecycle()
    val outingSearchUiState by viewModel.outingSearchUiState.collectAsStateWithLifecycle()
    val deleteOutingUiState by viewModel.deleteOutingUiState.collectAsStateWithLifecycle()

    when (deleteOutingUiState) {
        is Result.Success -> {
            viewModel.getOutingCount()
            viewModel.initDeleteOuting()
        }
        else -> Unit
    }

    OutingStatusScreen(
        role = if (role.isNotBlank()) Authority.valueOf(role) else Authority.ROLE_STUDENT,
        outingSearch = outingSearch,
        onOutingSearchChange = viewModel::onOutingSearchChange,
        getOutingListUiState = getOutingListUiState,
        getOutingCountUiState = getOutingCountUiState,
        outingSearchUiState = outingSearchUiState,
        onBackClick = onBackClick,
        onErrorToast = onErrorToast,
        outingSearchCallBack = viewModel::outingSearch,
        deleteOutingCallBack = viewModel::deleteOuting
    )
}

@Composable
private fun OutingStatusScreen(
    role: Authority,
    outingSearch: String,
    onOutingSearchChange: (String) -> Unit,
    getOutingListUiState: GetOutingListUiState,
    getOutingCountUiState: GetOutingCountUiState,
    outingSearchUiState: OutingSearchUiState,
    onBackClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
    outingSearchCallBack: (String) -> Unit,
    deleteOutingCallBack: (UUID) -> Unit
) {
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current
    val isKeyboardOpen by keyboardAsState()
    var openDialog by remember { mutableStateOf(false) }
    var uuid by remember { mutableStateOf(UUID.randomUUID()) }

    LaunchedEffect(isKeyboardOpen) {
        if (!isKeyboardOpen) {
            focusManager.clearFocus()
        }
    }

    if (openDialog) {
        GomsTwoButtonDialog(
            openDialog = openDialog,
            onStateChange = { openDialog = it },
            title = stringResource(id = R.string.forced_return_outing),
            content = stringResource(id = R.string.want_forced_return_outing),
            dismissText = stringResource(id = R.string.cancel),
            checkText = stringResource(id = R.string.return_outing),
            onDismissClick = { openDialog = false },
            onCheckClick = {
                deleteOutingCallBack(uuid)
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
            OutingStatusText(modifier = Modifier.align(Alignment.Start))
            GomsSpacer(size = SpacerSize.Small)
            GomsSearchTextField(
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                placeHolder = stringResource(id = R.string.student_search),
                setText = outingSearch,
                onValueChange = onOutingSearchChange,
                onSearchTextChange = outingSearchCallBack,
                singleLine = true
            )
            GomsSpacer(size = SpacerSize.ExtraSmall)
            OutingStatusList(
                role = role,
                getOutingListUiState = getOutingListUiState,
                getOutingCountUiState = getOutingCountUiState,
                outingSearchUiState = outingSearchUiState,
                onErrorToast = onErrorToast
            ) { selectedUuid ->
                uuid = selectedUuid
                openDialog = true
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun OutingStatusScreenPreview() {
    GomsTheme(ThemeType.SYSTEM.value) {
        OutingStatusScreen(
            role = Authority.ROLE_STUDENT_COUNCIL,
            outingSearch = "GOMS",
            onOutingSearchChange = {},
            getOutingListUiState = GetOutingListUiState.Loading,
            getOutingCountUiState = GetOutingCountUiState.Loading,
            outingSearchUiState = OutingSearchUiState.Loading,
            onBackClick = {},
            onErrorToast = { _, _ -> },
            outingSearchCallBack = {},
        ) {}
    }
}