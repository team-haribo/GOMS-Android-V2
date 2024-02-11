package com.goms.design_system.component.bottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.goms.design_system.component.button.AdminBottomSheetButton
import com.goms.design_system.theme.GomsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MultipleSelectorBottomSheet(
    modifier: Modifier,
    title: String,
    subTitle1: String,
    list1: List<String>,
    selected1: String,
    itemChange1: (String) -> Unit,
    subTitle2: String,
    list2: List<String>,
    selected2: String,
    itemChange2: (String) -> Unit,
    subTitle3: String,
    list3: List<String>,
    selected3: String,
    itemChange3: (String) -> Unit,
    closeSheet: () -> Unit
) {
    var componentWidth by remember { mutableStateOf( 0.dp ) }
    val density = LocalDensity.current

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    GomsTheme { colors, typography ->
        ModalBottomSheet(
            onDismissRequest = { closeSheet() },
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
                    closeSheet = closeSheet
                )
                Text(
                    text = subTitle1,
                    style = typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.WHITE
                )
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(list1.size) {
                        AdminBottomSheetButton(
                            modifier = Modifier.widthIn((componentWidth - 16.dp * list1.lastIndex) / list1.size),
                            text = list1[it],
                            selected = selected1 == list1[it]
                        ) {
                            itemChange1(list1[it])
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = subTitle2,
                    style = typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.WHITE
                )
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(list2.size) {
                        AdminBottomSheetButton(
                            modifier = Modifier.widthIn((componentWidth - 16.dp * list2.lastIndex) / list2.size),
                            text = list2[it],
                            selected = selected2 == list2[it]
                        ) {
                            itemChange2(list2[it])
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = subTitle3,
                    style = typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.WHITE
                )
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(list3.size) {
                        AdminBottomSheetButton(
                            modifier = Modifier.widthIn((componentWidth - 16.dp * list3.lastIndex) / list3.size),
                            text = list3[it],
                            selected = selected3 == list3[it]
                        ) {
                            itemChange3(list3[it])
                        }
                    }
                }
            }
        }
    }
}