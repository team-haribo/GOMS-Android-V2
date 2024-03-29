package com.goms.setting.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.goms.design_system.component.dropdown.GomsDropdown
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.theme.GomsTheme.typography
import kotlinx.collections.immutable.toPersistentList

@Composable
fun SelectThemeDropDown(
    modifier: Modifier,
    onThemeSelect: (Int) -> Unit,
    themeState: String
) {
    val dropdownList = listOf("다크(기본)", "라이트", "시스템 테마 설정")
    var selectedIndex by remember { mutableIntStateOf(0) }

    selectedIndex = when(themeState) {
        "Dark" -> 0
        "Light" -> 1
        "System" -> 2
        else -> 0
    }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "앱 테마 설정",
            style = typography.textMedium,
            color = colors.WHITE,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))
        GomsDropdown(
            dropdownList = dropdownList.toPersistentList(),
            dropdownListSize = dropdownList.size,
            selectedIndex = selectedIndex,
            modifier = Modifier.padding(horizontal = 20.dp),
            backgroundColor = colors.G1,
            onClick = {},
            onItemClick = {
                selectedIndex = it
                onThemeSelect(selectedIndex)
            }
        )
    }
}

