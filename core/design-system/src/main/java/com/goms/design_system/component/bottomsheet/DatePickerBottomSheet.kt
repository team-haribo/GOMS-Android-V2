package com.goms.design_system.component.bottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.goms.design_system.theme.GomsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerBottomSheet(
    state: DatePickerState,
    closeSheet: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    GomsTheme { colors, typography ->
        ModalBottomSheet(
            onDismissRequest = closeSheet,
            sheetState = sheetState,
            shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
            containerColor = colors.G1,
            dragHandle = { BottomSheetDefaults.DragHandle() },
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                BottomSheetHeader(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    title = "날짜 선택",
                    closeSheet = closeSheet
                )
                DatePicker(
                    state = state,
                    showModeToggle = false,
                    title = null,
                    headline = null,
                    colors = DatePickerDefaults.colors(
                        containerColor = colors.G1,
                        weekdayContentColor = colors.G4,
                        navigationContentColor = colors.WHITE,
                        yearContentColor = colors.WHITE,
                        disabledYearContentColor = colors.WHITE,
                        currentYearContentColor = colors.WHITE,
                        selectedYearContentColor = colors.WHITE,
                        dayContentColor = colors.WHITE,
                        disabledDayContentColor = colors.WHITE,
                        selectedDayContentColor = Color.White,
                        disabledSelectedDayContentColor = colors.WHITE,
                        selectedDayContainerColor = colors.A7,
                        disabledSelectedDayContainerColor = colors.G1,
                        todayDateBorderColor = colors.A7
                    )
                )
            }
        }
    }
}