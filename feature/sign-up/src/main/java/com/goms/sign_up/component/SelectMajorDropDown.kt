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
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.model.enum.Major
import kotlinx.collections.immutable.toPersistentList

@Composable
fun SelectMajorDropDown(
    onSelectMajor: (String) -> Unit,
    onClick: () -> Unit
) {
    val dropdownList =
        listOf(Major.SW_DEVELOP.fullName, Major.SMART_IOT.fullName, Major.AI.fullName)
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }

    GomsDropdown(
        dropdownList = dropdownList.toPersistentList(),
        dropdownListSize = dropdownList.size,
        useDefaultText = true,
        defaultText = "학과",
        selectedIndex = selectedIndex,
        modifier = Modifier.padding(horizontal = 20.dp),
        backgroundColor = colors.G1,
        onClick = onClick,
        onItemClick = {
            selectedIndex = it
            onSelectMajor(dropdownList[selectedIndex])
        }
    )
}
