package com.goms.main

import android.content.res.Configuration
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.goms.design_system.component.bottomsheet.DatePickerBottomSheet
import com.goms.design_system.component.spacer.GomsSpacer
import com.goms.design_system.component.spacer.SpacerSize
import com.goms.design_system.theme.GomsTheme
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.theme.ThemeType
import com.goms.design_system.util.getDefaultWednesday
import com.goms.main.component.LateList
import com.goms.main.component.LateListText
import com.goms.main.viewmodel.uistate.GetLateListUiState
import com.goms.main.viewmodel.MainViewModel
import com.goms.model.enum.Authority
import com.goms.model.util.ResourceKeys
import com.goms.ui.GomsRoleBackButton
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
internal fun LateListRoute(
    onBackClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
    viewModel: MainViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
) {
    val role by viewModel.role.collectAsStateWithLifecycle(initialValue = ResourceKeys.EMPTY)
    val getLateListUiState by viewModel.getLateListUiState.collectAsStateWithLifecycle()

    LateListScreen(
        role = if (role.isNotBlank()) Authority.valueOf(role) else Authority.ROLE_STUDENT,
        getLateListUiState = getLateListUiState,
        onBackClick = onBackClick,
        lateListCallBack = viewModel::getLateList,
        onErrorToast = onErrorToast
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LateListScreen(
    role: Authority,
    getLateListUiState: GetLateListUiState,
    onBackClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
    lateListCallBack: (LocalDate) -> Unit
) {
    val scrollState = rememberScrollState()
    val datePickerState = rememberDatePickerState()
    var onDatePickerBottomSheetOpenClick by remember { mutableStateOf(false) }
    val selectedDateMillis =
        datePickerState.selectedDateMillis ?: getDefaultWednesday().toEpochMilliseconds()
    val selectedInstant = Instant.fromEpochMilliseconds(selectedDateMillis)
    val selectedLocalDateTime = selectedInstant.toLocalDateTime(TimeZone.currentSystemDefault())
    val selectedLocalDate = selectedLocalDateTime.date

    LaunchedEffect(true) {
        lateListCallBack(selectedLocalDate)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.BACKGROUND)
            .statusBarsPadding()
            .navigationBarsPadding()
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
            LateListText(modifier = Modifier.align(Alignment.Start))
            GomsSpacer(size = SpacerSize.ExtraSmall)
            LateList(
                getLateListUiState = getLateListUiState,
                localDate = selectedLocalDate,
                onBottomSheetOpenClick = { onDatePickerBottomSheetOpenClick = true },
                onErrorToast = onErrorToast
            )
        }
    }
    if (onDatePickerBottomSheetOpenClick) {
        DatePickerBottomSheet(
            state = datePickerState,
            closeSheet = {
                onDatePickerBottomSheetOpenClick = false
                lateListCallBack(selectedLocalDate)
            }
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun LateListScreenPreview() {
    GomsTheme(ThemeType.SYSTEM.value) {
        LateListScreen(
            role = Authority.ROLE_STUDENT_COUNCIL,
            getLateListUiState = GetLateListUiState.Loading,
            onBackClick = {},
            onErrorToast = { _, _ -> },
        ) {}
    }
}