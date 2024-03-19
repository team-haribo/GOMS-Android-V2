package com.goms.main

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.goms.design_system.component.bottomsheet.DatePickerBottomSheet
import com.goms.design_system.component.button.GomsBackButton
import com.goms.design_system.theme.GomsTheme
import com.goms.design_system.util.getDefaultWednesday
import com.goms.main.component.LateList
import com.goms.main.component.LateListText
import com.goms.main.viewmodel.GetLateListUiState
import com.goms.main.viewmodel.MainViewModel
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun LateListRoute(
    onBackClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: String?) -> Unit,
    viewModel: MainViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
) {
    val getLateListUiState by viewModel.getLateListUiState.collectAsStateWithLifecycle()

    LateListScreen(
        getLateListUiState = getLateListUiState,
        onBackClick = onBackClick,
        lateListCallBack = { viewModel.getLateList(it) },
        onErrorToast = onErrorToast
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LateListScreen(
    getLateListUiState: GetLateListUiState,
    onBackClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: String?) -> Unit,
    lateListCallBack: (LocalDate) -> Unit
) {
    val scrollState = rememberScrollState()
    val datePickerState = rememberDatePickerState()
    var onDatePickerBottomSheetOpenClick by remember { mutableStateOf(false) }
    val selectedDateMillis = datePickerState.selectedDateMillis ?: getDefaultWednesday().toEpochMilliseconds()
    val selectedInstant = Instant.fromEpochMilliseconds(selectedDateMillis)
    val selectedLocalDateTime = selectedInstant.toLocalDateTime(TimeZone.currentSystemDefault())
    val selectedLocalDate = selectedLocalDateTime.date

    LaunchedEffect(true) {
        lateListCallBack(selectedLocalDate)
    }

    GomsTheme { colors, typography ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.BACKGROUND)
                .statusBarsPadding()
                .navigationBarsPadding()
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
                LateListText(modifier = Modifier.align(Alignment.Start))
                Spacer(modifier = Modifier.height(8.dp))
                LateList(
                    getLateListUiState = getLateListUiState,
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
}