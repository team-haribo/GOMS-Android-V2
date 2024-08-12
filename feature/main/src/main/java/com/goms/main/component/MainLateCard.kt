package com.goms.main.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.goms.design_system.component.clickable.gomsClickable
import com.goms.design_system.component.shimmer.shimmerEffect
import com.goms.design_system.component.spacer.GomsSpacer
import com.goms.design_system.component.spacer.SpacerSize
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.theme.GomsTheme.typography
import com.goms.main.R
import com.goms.main.data.RankData
import com.goms.main.data.toData
import com.goms.main.viewmodel.uistate.GetLateRankListUiState
import com.goms.model.enum.Authority
import com.goms.ui.toText

@Composable
internal fun MainLateCard(
    modifier: Modifier = Modifier,
    role: Authority,
    getLateRankListUiState: GetLateRankListUiState,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
    onClick: () -> Unit
) {
    var componentWidth by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    Surface(
        modifier = modifier
            .onGloballyPositioned {
                componentWidth = with(density) {
                    it.size.width.toDp()
                }
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colors.BACKGROUND)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.late_top_3),
                    style = typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = colors.WHITE
                )
                if (role == Authority.ROLE_STUDENT_COUNCIL) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(colors.G1)
                            .gomsClickable(
                                isIndication = true,
                                rippleColor = colors.G7.copy(0.5f)
                            ) {
                                onClick()
                            }
                            .padding(horizontal = 8.dp, vertical = 2.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.see_more_detail),
                            style = typography.buttonSmall,
                            fontWeight = FontWeight.Normal,
                            color = colors.G7
                        )
                    }
                }
            }
            GomsSpacer(size = SpacerSize.Small)
            when (getLateRankListUiState) {
                is GetLateRankListUiState.Loading -> {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(3) {
                            ShimmerMainLateItem(
                                modifier = Modifier
                                    .widthIn((componentWidth - 24.dp) / 3)
                                    .height(136.dp)
                            )
                        }
                    }
                }

                is GetLateRankListUiState.Empty -> {
                    RankEmptyText()
                }

                is GetLateRankListUiState.Success -> {
                    val list = getLateRankListUiState.getLateRankListResponse
                    val takeList by remember(list) { mutableStateOf(list.take(3)) }

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = takeList,
                            key = { data ->
                                data.accountIdx
                            }
                        ) { data ->
                            MainLateItem(
                                modifier = Modifier
                                    .widthIn((componentWidth - 24.dp) / 3)
                                    .height(136.dp),
                                data = data.toData()
                            )
                        }
                    }
                }

                is GetLateRankListUiState.Error -> {
                    onErrorToast(getLateRankListUiState.exception, R.string.error_get_late_rank_list)
                }
            }
        }
    }
}

@Composable
private fun MainLateItem(
    modifier: Modifier = Modifier,
    data: RankData
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(color = colors.G1)
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (data.profileUrl.isNullOrEmpty()) {
            Image(
                painter = painterResource(com.goms.design_system.R.drawable.ic_profile),
                contentDescription = stringResource(id = R.string.default_profile_image),
                modifier = Modifier.size(56.dp)
            )
        } else {
            AsyncImage(
                model = data.profileUrl,
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(40.dp)),
                contentScale = ContentScale.Crop,
                contentDescription = stringResource(id = R.string.profile_image),
            )
        }
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

@Composable
private fun ShimmerMainLateItem(modifier: Modifier) {
    Box(
        modifier = modifier
            .shimmerEffect(
                color = colors.WHITE,
                shape = RoundedCornerShape(8.dp)
            )
    )
}
