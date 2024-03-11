package com.goms.setting.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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

@Composable
fun SelectThemeDropDown(
    modifier: Modifier,
) {
    var dropdownState by remember { mutableStateOf(DropdownState.Hide.name) }
    val dropdownList = listOf("다크(기본)","라이트","시스템 테마 설정")
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }

    LaunchedEffect(dropdownState) {
        if (dropdownState == DropdownState.OnDissmiss.name) {
            dropdownState = DropdownState.Hide.name
        }
    }

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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .border(
                        BorderStroke(
                            width = 1.dp,
                            color = colors.WHITE.copy(0.15f)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clip(RoundedCornerShape(12.dp))
                    .background(colors.G1)
                    .padding(12.dp)
                    .gomsClickable {
                        dropdownState = when (dropdownState) {
                            DropdownState.Show.name -> DropdownState.Hide.name
                            DropdownState.Hide.name -> DropdownState.Show.name
                            else -> dropdownState
                        }
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = dropdownList[selectedIndex],
                    style = typography.textMedium,
                    color = colors.G7,
                    fontWeight = FontWeight.Normal
                )
                if(dropdownState == DropdownState.Show.name) {
                    ChevronDownIcon(
                        tint = colors.G7
                    )
                } else {
                    ChevronUpIcon(
                        tint = colors.G7
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            GomsDropdown(
                dropdownList = dropdownList,
                dropdownListSize = dropdownList.size,
                showDropdown = dropdownState,
                selectedIndex = selectedIndex,
                modifier = Modifier.padding(horizontal = 20.dp),
                backgroundColor = colors.G1,
                onDissmiss = { dropdownState = DropdownState.OnDissmiss.name },
                onItemClick = {
                    selectedIndex = it
                    dropdownState = DropdownState.Hide.name
                }
            )

        }
    }
}
