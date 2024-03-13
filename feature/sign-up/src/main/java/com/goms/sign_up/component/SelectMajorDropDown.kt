package com.goms.sign_up.component

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.goms.design_system.component.dropdown.GomsDropdown
import com.goms.design_system.theme.GomsTheme
import com.goms.model.enum.Major

@Composable
fun SelectMajorDropDown(
    onSelectMajor: (String) -> Unit,
    onClick: () -> Unit
) {
    val dropdownList = listOf(Major.SW_DEVELOP.fullName, Major.SMART_IOT.fullName, Major.AI.fullName)
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }

    GomsTheme { colors, typography ->

        GomsDropdown(
            dropdownList = dropdownList,
            dropdownListSize = dropdownList.size,
            selectedIndex = selectedIndex,
            modifier = Modifier.padding(horizontal = 20.dp),
            backgroundColor = colors.G1,
            onDissmiss = { onClick() },
            onItemClick = {
                selectedIndex = it
                onSelectMajor(dropdownList[selectedIndex])
            }
        )
    }
}