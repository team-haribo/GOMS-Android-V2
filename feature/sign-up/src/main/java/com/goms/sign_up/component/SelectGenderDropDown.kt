package com.goms.sign_up.component

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.goms.design_system.component.dropdown.GomsDropdown
import com.goms.design_system.theme.GomsTheme
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.model.enum.Gender
import kotlinx.collections.immutable.toPersistentList

@Composable
fun SelectGenderDropDown(
    onSelectGender: (String) -> Unit,
    onClick: () -> Unit
) {
    val dropdownList = listOf(Gender.MAN.value, Gender.WOMAN.value)
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }
    var useDefaultText by remember { mutableStateOf(true) }

    GomsDropdown(
        dropdownList = dropdownList.toPersistentList(),
        dropdownListSize = dropdownList.size,
        useDefaultText = useDefaultText,
        defaultText = "성별",
        selectedIndex = selectedIndex,
        modifier = Modifier.padding(horizontal = 20.dp),
        backgroundColor = colors.G1,
        onClick = onClick,
        onItemClick = {
            useDefaultText = false
            selectedIndex = it
            onSelectGender(dropdownList[selectedIndex])
        }
    )
}
