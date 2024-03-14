package com.goms.sign_up.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.goms.design_system.component.clickable.gomsClickable
import com.goms.design_system.component.dropdown.DropdownState
import com.goms.design_system.component.dropdown.GomsDropdown
import com.goms.design_system.icon.ChevronDownIcon
import com.goms.design_system.icon.ChevronUpIcon
import com.goms.design_system.theme.GomsTheme
import com.goms.model.enum.Major

@Composable
fun SelectMajorDropDown(
    modifier: Modifier,
    major: String,
    onSelectMajor: (String) -> Unit,
    onClick: () -> Unit
) {
    var dropdownState by remember { mutableStateOf(DropdownState.Hide.name) }
    val dropdownList = listOf(Major.SW_DEVELOP.fullName, Major.SMART_IOT.fullName, Major.AI.fullName)
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }

    val derivedDropdownState = remember(dropdownState) {
        derivedStateOf {
            dropdownState
        }
    }

    LaunchedEffect(dropdownState) {
        if (dropdownState == DropdownState.OnDissmiss.name) {
            dropdownState = DropdownState.Hide.name
        }
    }

    GomsTheme { colors, typography ->
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(64.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(colors.G1)
                .border(
                    width = 1.dp,
                    color = colors.WHITE.copy(0.15f),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(12.dp)
                .gomsClickable {
                    when (dropdownState) {
                        DropdownState.Show.name -> dropdownState = DropdownState.Hide.name
                        DropdownState.Hide.name -> dropdownState = DropdownState.Show.name
                    }
                    onClick()
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = major.ifBlank { "학과" },
                style = typography.textMedium,
                color = if (major.isNotBlank()) colors.WHITE else colors.G7,
                fontWeight = FontWeight.Normal
            )
            if (derivedDropdownState.value == DropdownState.Show.name) {
                ChevronDownIcon(
                    tint = colors.G7
                )
            } else {
                ChevronUpIcon(
                    tint = colors.WHITE
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        GomsDropdown(
            dropdownList = dropdownList,
            dropdownListSize = dropdownList.size,
            showDropdown = derivedDropdownState.value,
            selectedIndex = selectedIndex,
            modifier = Modifier.padding(horizontal = 20.dp),
            backgroundColor = colors.G1,
            onDissmiss = {
                dropdownState = DropdownState.OnDissmiss.name
            },
            onItemClick = {
                selectedIndex = it
                dropdownState = DropdownState.Hide.name
                onSelectMajor(dropdownList[selectedIndex])
            }
        )
    }
}