package com.goms.design_system.component.bottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.goms.design_system.R
import com.goms.design_system.component.button.AdminBottomSheetButton
import com.goms.design_system.component.button.InitBottomSheetButton
import com.goms.design_system.component.spacer.GomsSpacer
import com.goms.design_system.component.spacer.SpacerSize
import com.goms.design_system.theme.GomsTheme
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.theme.GomsTheme.typography
import com.goms.design_system.theme.ThemeType
import com.goms.design_system.util.ThemePreviews
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

@Immutable
data class ListData(
    val list: PersistentList<PersistentList<String>>
)

const val EMPTY = ""

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MultipleSelectorBottomSheet(
    modifier: Modifier = Modifier,
    title: String,
    subTitles: PersistentList<String>,
    lists: ListData,
    selectedItems: PersistentList<String>,
    itemChanges: PersistentList<(String) -> Unit>,
    initClick: () -> Unit,
    closeSheet: () -> Unit
) {
    var componentWidth by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val bottomSheetSelectedItems = remember { selectedItems.toMutableStateList() }

    LaunchedEffect(selectedItems) {
        bottomSheetSelectedItems.clear()
        bottomSheetSelectedItems.addAll(selectedItems)
    }

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
            LazyColumn {
                itemsIndexed(
                    items = subTitles,
                    key = { index, data ->
                        data
                    }
                ) { index, subTitle ->
                    MultipleSelectorBottomSheetItem(
                        componentWidth = componentWidth,
                        title = subTitle,
                        list = lists.list[index],
                        selectedItem = bottomSheetSelectedItems[index],
                    ) {
                        itemChanges[index](it)
                        bottomSheetSelectedItems[index] = it
                    }
                }
            }
            InitBottomSheetButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.initialize_filter)
            ) {
                bottomSheetSelectedItems.indices.forEach { bottomSheetSelectedItems[it] = EMPTY }
                initClick()
            }
        }
    }
}

@Composable
fun MultipleSelectorBottomSheetItem(
    componentWidth: Dp,
    title: String,
    list: PersistentList<String>,
    selectedItem: String,
    itemChange: (String) -> Unit
) {
    Text(
        text = title,
        style = typography.titleSmall,
        fontWeight = FontWeight.SemiBold,
        color = colors.WHITE
    )
    GomsSpacer(size = SpacerSize.ExtraSmall)
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = list,
            key = { data ->
                data
            }
        ) { data ->
            AdminBottomSheetButton(
                modifier = Modifier.widthIn((componentWidth - 16.dp * (list.size - 1)) / list.size),
                text = data,
                selected = selectedItem == data
            ) {
                if (selectedItem == data) {
                    itemChange(EMPTY)
                } else {
                    itemChange(data)
                }
            }
        }
    }
    GomsSpacer(size = SpacerSize.Large)
}

@ThemePreviews
@Composable
private fun MultipleSelectorBottomSheetPreview() {
    GomsTheme(ThemeType.SYSTEM.value) {
        MultipleSelectorBottomSheet(
            title = "GOMS",
            subTitles = listOf("곰", "하리보").toPersistentList(),
            lists = ListData(
                list = listOf(
                    listOf("반달가슴곰", "불곰").toPersistentList(),
                    listOf("빨강", "노랑").toPersistentList()
                ).toPersistentList()
            ),
            selectedItems = listOf("반달가슴곰", "노랑").toPersistentList(),
            itemChanges = persistentListOf(),
            initClick = {}
        ) {}
    }
}
