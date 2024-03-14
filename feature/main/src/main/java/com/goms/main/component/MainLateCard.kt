package com.goms.main.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.goms.design_system.R
import com.goms.design_system.component.clickable.gomsClickable
import com.goms.design_system.component.shimmer.shimmerEffect
import com.goms.design_system.theme.GomsTheme
import com.goms.main.data.RankData
import com.goms.main.data.toData
import com.goms.main.viewmodel.GetLateRankListUiState
import com.goms.model.enum.Authority
import com.goms.ui.toText

@Composable
fun MainLateCard(
    modifier: Modifier = Modifier,
    role: Authority,
    getLateRankListUiState: GetLateRankListUiState,
    onErrorToast: (throwable: Throwable?, message: String?) -> Unit,
    onClick: () -> Unit
) {
    var componentWidth by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    GomsTheme { colors, typography ->
        Surface(
            modifier = modifier
                .onGloballyPositioned {
                    componentWidth = with(density) {
                        it.size.width.toDp()
                    }
                },
            color = colors.G1,
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(width = 1.dp, color = colors.WHITE.copy(0.15f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "지각자 TOP 3",
                        style = typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = colors.WHITE
                    )
                    if (role == Authority.ROLE_STUDENT_COUNCIL) {
                        Text(
                            modifier = Modifier.gomsClickable { onClick() },
                            text = "더보기",
                            style = typography.buttonSmall,
                            fontWeight = FontWeight.Normal,
                            color = colors.G7
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                when (getLateRankListUiState) {
                    GetLateRankListUiState.Loading -> {
                        LazyRow(modifier = Modifier.fillMaxWidth()) {
                            items(3) {
                                ShimmerMainLateItem(modifier = Modifier.widthIn((componentWidth - 32.dp) / 3))
                            }
                        }
                    }
                    GetLateRankListUiState.Empty -> {
                        LateListEmptyText()
                    }
                    is GetLateRankListUiState.Success -> {
                        val list = getLateRankListUiState.getLateRankListResponse

                        LazyRow(modifier = Modifier.fillMaxWidth()) {
                            items(list.take(3).size) {
                                MainLateItem(
                                    modifier = Modifier.widthIn((componentWidth - 32.dp) / 3),
                                    data = list[it].toData()
                                )
                            }
                        }
                    }
                    is GetLateRankListUiState.Error -> {
                        onErrorToast(getLateRankListUiState.exception, "지각자 랭킹 정보를 가져오지 못했습니다")
                    }
                }
            }
        }
    }
}

@Composable
fun MainLateItem(
    modifier: Modifier = Modifier,
    data: RankData
) {
    GomsTheme { colors, typography ->
        Column(
            modifier = modifier.padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (data.profileUrl.isNullOrEmpty()) {
                Image(
                    painter = painterResource(R.drawable.ic_profile),
                    contentDescription = "Default Profile Image",
                    modifier = Modifier.size(56.dp)
                )
            } else {
                AsyncImage(
                    model = data.profileUrl,
                    modifier = Modifier.size(56.dp),
                    contentScale = ContentScale.Crop,
                    contentDescription = "Profile Image",
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
}

@Composable
fun ShimmerMainLateItem(modifier: Modifier) {
    GomsTheme { colors, typography ->
        Column(
            modifier = modifier.padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .shimmerEffect(color = colors.WHITE)
            )
            Box(
                modifier = Modifier
                    .size(56.dp, 23.dp)
                    .shimmerEffect(color = colors.WHITE)
            )
            Box(
                modifier = Modifier
                    .size(60.dp, 16.dp)
                    .shimmerEffect(color = colors.WHITE)
            )
        }
    }
}