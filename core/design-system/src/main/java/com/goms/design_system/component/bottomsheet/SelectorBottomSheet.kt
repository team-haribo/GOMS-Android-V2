package com.goms.design_system.component.bottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.goms.design_system.component.button.BottomSheetButton
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.util.gomsPreview
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectorBottomSheet(
    modifier: Modifier = Modifier,
    title: String,
    list: PersistentList<String>,
    selected: String,
    itemChange: (String) -> Unit,
    closeSheet: () -> Unit
) {
    var componentWidth by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    val sheetState = rememberModalBottomSheetState()

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
                }
        ) {
            BottomSheetHeader(
                modifier = Modifier,
                title = title,
                closeSheet = closeSheet
            )
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(list.size) {
                    BottomSheetButton(
                        modifier = Modifier.widthIn((componentWidth - 16.dp * list.lastIndex) / list.size),
                        text = list[it],
                        selected = selected == list[it]
                    ) {
                        itemChange(list[it])
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun SelectorBottomSheetPreview() {
    gomsPreview(
        content = {
            SelectorBottomSheet(
                title = "바텀 시트",
                list = listOf("1", "2").toPersistentList(),
                selected = "1",
                itemChange = {}
            ) {}
        }
    )
}
