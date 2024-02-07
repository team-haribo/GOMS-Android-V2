package com.goms.main

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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.goms.design_system.component.bottomsheet.DatePickerBottomSheet
import com.goms.design_system.component.button.GomsBackButton
import com.goms.design_system.component.modifier.gomsClickable
import com.goms.design_system.component.textfield.GomsSearchTextField
import com.goms.design_system.theme.GomsTheme
import com.goms.design_system.util.getDefaultWednesday
import com.goms.design_system.util.keyboardAsState
import com.goms.main.component.LateList
import com.goms.main.component.LateListText
import java.time.Instant
import java.time.ZoneId

@Composable
fun LateListRoute(
    onBackClick: () -> Unit
) {
    LateListScreen(
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LateListScreen(
    onBackClick: () -> Unit,
) {
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current
    val isKeyboardOpen by keyboardAsState()
    val datePickerState = rememberDatePickerState()
    var onDatePickerBottomSheetOpenClick by remember { mutableStateOf(false) }
    val selectedDate = Instant.ofEpochMilli(datePickerState.selectedDateMillis ?: getDefaultWednesday().toEpochMilli())
        .atZone(ZoneId.systemDefault())
        .toLocalDate()

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
                LateListText(modifier = Modifier.align(Alignment.Start))
                Spacer(modifier = Modifier.height(8.dp))
                LateList(
                    onBottomSheetOpenClick = { onDatePickerBottomSheetOpenClick = true }
                )
            }
        }
        if (onDatePickerBottomSheetOpenClick) {
            DatePickerBottomSheet(
                state = datePickerState,
                closeSheet = { onDatePickerBottomSheetOpenClick = false }
            )
        }
    }
}