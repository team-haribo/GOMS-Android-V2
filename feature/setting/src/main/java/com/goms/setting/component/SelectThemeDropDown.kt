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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.goms.design_system.component.dropdown.GomsDropdown
import com.goms.design_system.component.spacer.GomsSpacer
import com.goms.design_system.component.spacer.SpacerSize
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.theme.GomsTheme.typography
import com.goms.design_system.theme.ThemeType
import com.goms.setting.R
import kotlinx.collections.immutable.toPersistentList

@Composable
internal fun SelectThemeDropDown(
    modifier: Modifier,
    onThemeSelect: (Int) -> Unit,
    themeState: String
) {
    val dropdownList = listOf(ThemeType.DARK.kr, ThemeType.LIGHT.kr, ThemeType.SYSTEM.kr)
    var selectedIndex by remember { mutableIntStateOf(0) }

    selectedIndex = when(themeState) {
        ThemeType.DARK.value -> 0
        ThemeType.LIGHT.value -> 1
        ThemeType.SYSTEM.value -> 2
        else -> 0
    }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.app_theme_setting),
            style = typography.textMedium,
            color = colors.WHITE,
            fontWeight = FontWeight.SemiBold
        )
        GomsSpacer(size = SpacerSize.ExtraSmall)
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

