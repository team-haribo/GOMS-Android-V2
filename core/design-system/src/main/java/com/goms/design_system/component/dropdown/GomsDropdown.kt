package com.goms.design_system.component.dropdown

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.goms.design_system.component.clickable.gomsClickable
import com.goms.design_system.theme.GomsTheme

enum class DropdownState(val value: String) {
    Show("Show"),
    Hide("Hide"),
    OnDissmiss("OnDissmiss"),
}

@Composable
fun GomsDropdown(
    dropdownList: List<String>,
    dropdownListSize: Int,
    onDissmiss: () -> Unit,
    showDropdown: String,
    selectedIndex: Int,
    modifier: Modifier,
    height: Dp = 64.dp,
    backgroundColor: Color = Color.Unspecified,
    onItemClick: (Int) -> Unit
) {
    val scrollState = rememberScrollState()
    var dropdownType: Boolean? by remember { mutableStateOf(null) }

    GomsTheme { colors, typography ->
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Box() {
                if (showDropdown == DropdownState.Show.name) {
                    Popup(
                        alignment = Alignment.TopCenter,
                        properties = PopupProperties(
                            excludeFromSystemGesture = true,
                        ),
                        onDismissRequest = onDissmiss
                    ) {
                        Column(
                            modifier = modifier
                                .background(Color.Transparent)
                                .verticalScroll(state = scrollState),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            dropdownList.onEachIndexed { index, item ->
                                dropdownType = when (index) {
                                    0 -> true
                                    dropdownListSize - 1 -> false
                                    else -> null
                                }

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(height)
                                        .border(
                                            BorderStroke(
                                                width = 1.dp,
                                                color = colors.WHITE.copy(0.15f)
                                            ),
                                            shape =
                                                when(dropdownType) {
                                                    true -> RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                                                    false -> RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
                                                    else -> RoundedCornerShape(0.dp)
                                                }
                                        )
                                        .clip(
                                            when(dropdownType) {
                                                true -> RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                                                false -> RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
                                                else -> RoundedCornerShape(0.dp)
                                            }
                                        )
                                        .background(backgroundColor)
                                        .padding(12.dp)
                                        .gomsClickable {
                                            onItemClick(index)
                                        },
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    Text(
                                        text = item,
                                        style = typography.textMedium,
                                        color = colors.WHITE,
                                        fontWeight = FontWeight.Normal
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}