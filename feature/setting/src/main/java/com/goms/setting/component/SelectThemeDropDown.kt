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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.goms.design_system.component.dropdown.GomsDropdown
import com.goms.design_system.theme.GomsTheme
import kotlinx.collections.immutable.toPersistentList

@Composable
fun SelectThemeDropDown(
    modifier: Modifier,
) {
    val dropdownList = listOf("다크(기본)","라이트","시스템 테마 설정")
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }

    GomsTheme { colors, typography ->
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
                }
            )
        }
    }
}
