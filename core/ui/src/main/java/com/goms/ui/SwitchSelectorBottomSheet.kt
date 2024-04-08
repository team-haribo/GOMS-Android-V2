package com.goms.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.goms.design_system.component.bottomsheet.BottomSheetHeader
import com.goms.design_system.component.button.GomsSwitchButton
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.theme.GomsTheme.typography
import com.goms.model.enum.Authority
import com.goms.model.enum.BlackList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwitchSelectorBottomSheet(
    modifier: Modifier,
    title: String,
    outing: String,
    role: String,
    closeSheet: (String, String) -> Unit
) {
    var componentWidth by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    val sheetState = rememberModalBottomSheetState()

    var outingState by remember { mutableStateOf(BlackList.NO_BLACK_LIST) }
    var roleState by remember { mutableStateOf(Authority.ROLE_STUDENT) }

    LaunchedEffect(outing, role) {
        outingState = if (outing == BlackList.BLACK_LIST.name) BlackList.BLACK_LIST else BlackList.NO_BLACK_LIST
        roleState = if (role == Authority.ROLE_STUDENT_COUNCIL.name) Authority.ROLE_STUDENT_COUNCIL else Authority.ROLE_STUDENT
    }

    ModalBottomSheet(
        onDismissRequest = { closeSheet(outingState.name, roleState.name) },
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        containerColor = colors.G1
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 16.dp)
                .onGloballyPositioned {
                    componentWidth = with(density) {
                        it.size.width.toDp()
                    }
                },
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            BottomSheetHeader(
                modifier = Modifier,
                title = title,
                closeSheet = { closeSheet(outingState.name, roleState.name) }
            )
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "외출금지",
                        style = typography.textMedium,
                        color = colors.WHITE,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "이 학생은 외출을 할 수 없어요",
                        style = typography.caption,
                        color = colors.G4,
                        fontWeight = FontWeight.Normal
                    )
                }
                GomsSwitchButton(
                    stateOn = 1,
                    stateOff = 0,
                    switchOnBackground = colors.P5,
                    switchOffBackground = colors.G4,
                    initialValue = if (outing == BlackList.BLACK_LIST.name) 1 else 0,
                    onCheckedChanged = {
                        outingState = if (it) BlackList.BLACK_LIST else BlackList.NO_BLACK_LIST
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "학생회 권한 부여",
                        style = typography.textMedium,
                        color = colors.WHITE,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "이 학생은 학생회에요",
                        style = typography.caption,
                        color = colors.G4,
                        fontWeight = FontWeight.Normal
                    )
                }
                GomsSwitchButton(
                    stateOn = 1,
                    stateOff = 0,
                    switchOnBackground = colors.P5,
                    switchOffBackground = colors.G4,
                    initialValue = if (role == Authority.ROLE_STUDENT_COUNCIL.name) 1 else 0,
                    onCheckedChanged = {
                        roleState = if (it) Authority.ROLE_STUDENT_COUNCIL else Authority.ROLE_STUDENT
                    }
                )
            }
        }
    }
}