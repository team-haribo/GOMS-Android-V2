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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.goms.design_system.R
import com.goms.design_system.component.button.AdminBottomSheetButton
import com.goms.design_system.component.button.InitBottomSheetButton
import com.goms.design_system.component.spacer.GomsSpacer
import com.goms.design_system.component.spacer.SpacerSize
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.theme.GomsTheme.typography
import kotlinx.collections.immutable.PersistentList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MultipleSelectorBottomSheet(
    modifier: Modifier,
    title: String,
    subTitle1: String,
    list1: PersistentList<String>,
    selected1: String,
    itemChange1: (String) -> Unit,
    subTitle2: String,
    list2: PersistentList<String>,
    selected2: String,
    itemChange2: (String) -> Unit,
    subTitle3: String,
    list3: PersistentList<String>,
    selected3: String,
    itemChange3: (String) -> Unit,
    subTitle4: String,
    list4: PersistentList<String>,
    selected4: String,
    itemChange4: (String) -> Unit,
    initClick: () -> Unit,
    closeSheet: () -> Unit
) {
    var componentWidth by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var bottomSheetSelected1 by remember { mutableStateOf(selected1) }
    var bottomSheetSelected2 by remember { mutableStateOf(selected2) }
    var bottomSheetSelected3 by remember { mutableStateOf(selected3) }
    var bottomSheetSelected4 by remember { mutableStateOf(selected4) }

    LaunchedEffect(selected1, selected2, selected3, selected4) {
        bottomSheetSelected1 = selected1
        bottomSheetSelected2 = selected2
        bottomSheetSelected3 = selected3
        bottomSheetSelected4 = selected4
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
                        selected = bottomSheetSelected1 == list1[it]
                    ) {
                        itemChange1(list1[it])
                    }
                }
            }
            GomsSpacer(size = SpacerSize.ExtraSmall)
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
                        selected = bottomSheetSelected2 == list2[it]
                    ) {
                        itemChange2(list2[it])
                    }
                }
            }
            GomsSpacer(size = SpacerSize.ExtraSmall)
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
                        selected = bottomSheetSelected3 == list3[it]
                    ) {
                        itemChange3(list3[it])
                    }
                }
            }
            GomsSpacer(size = SpacerSize.ExtraSmall)
            Text(
                text = subTitle4,
                style = typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = colors.WHITE
            )
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(list4.size) {
                    AdminBottomSheetButton(
                        modifier = Modifier.widthIn((componentWidth - 16.dp * list4.lastIndex) / list4.size),
                        text = list4[it],
                        selected = bottomSheetSelected4 == list4[it]
                    ) {
                        itemChange4(list4[it])
                    }
                }
            }
            GomsSpacer(size = SpacerSize.Large)
            InitBottomSheetButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.initialize_filter)
            ) {
                bottomSheetSelected1 = ""
                bottomSheetSelected2 = ""
                bottomSheetSelected3 = ""
                bottomSheetSelected4 = ""
                initClick()
            }
        }
    }
}
