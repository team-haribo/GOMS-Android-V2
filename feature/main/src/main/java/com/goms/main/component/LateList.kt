package com.goms.main.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.goms.design_system.component.shimmer.shimmerEffect
import com.goms.design_system.component.spacer.GomsSpacer
import com.goms.design_system.component.spacer.SpacerSize
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.theme.GomsTheme.typography
import com.goms.main.R
import com.goms.main.data.LateData
import com.goms.main.data.toData
import com.goms.main.viewmodel.uistate.GetLateListUiState
import com.goms.ui.toText

@Composable
internal fun LateList(
    modifier: Modifier = Modifier,
    getLateListUiState: GetLateListUiState,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
    onBottomSheetOpenClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SearchResultText(modifier = Modifier)
            FilterText(onFilterTextClick = onBottomSheetOpenClick)
        }
        when (getLateListUiState) {
            is GetLateListUiState.Loading -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 10000.dp)
                ) {
                    items(10) {
                        ShimmerLateListItem(modifier = modifier)
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth(),
                            color = colors.WHITE.copy(0.15f)
                        )
                    }
                }
            }

            is GetLateListUiState.Error -> {
                onErrorToast(getLateListUiState.exception, R.string.error_get_late_list)
            }

            is GetLateListUiState.Empty -> {
                Column(modifier = Modifier.fillMaxWidth()) {
                    GomsSpacer(height = 248.dp)
                    LateListEmptyText()
                }
            }

            is GetLateListUiState.Success -> {
                val list = getLateListUiState.getLateRankListResponse

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 10000.dp)
                ) {
                    items(
                        items = list,
                        key = { data ->
                            data.accountIdx
                        }
                    ) { data ->
                        LateListItem(
                            modifier = Modifier.fillMaxWidth(),
                            data = data.toData()
                        )
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth(),
                            color = colors.WHITE.copy(0.15f)
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun LateListItem(
    modifier: Modifier = Modifier,
    data: LateData
) {
    Row(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (data.profileUrl.isNullOrEmpty()) {
            Image(
                painter = painterResource(com.goms.design_system.R.drawable.ic_profile),
                contentDescription = stringResource(id = R.string.default_profile_image),
                modifier = Modifier.size(48.dp)
            )
        } else {
            AsyncImage(
                model = data.profileUrl,
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(40.dp)),
                contentScale = ContentScale.Crop,
                contentDescription = stringResource(id = R.string.profile_image),
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = data.name,
                style = typography.textMedium,
                fontWeight = FontWeight.SemiBold,
                color = colors.G7
            )
            Text(
                text = "${data.grade}기 | ${data.major.toText()}",
                style = typography.caption,
                fontWeight = FontWeight.Normal,
                color = colors.G4
            )
        }
    }
}

@Composable
private fun ShimmerLateListItem(modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .shimmerEffect(color = colors.WHITE)
        )
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Box(
                modifier = Modifier
                    .size(42.dp, 18.dp)
                    .shimmerEffect(color = colors.WHITE)
            )
            Box(
                modifier = Modifier
                    .size(50.dp, 14.dp)
                    .shimmerEffect(color = colors.WHITE)
            )
        }
    }
}
