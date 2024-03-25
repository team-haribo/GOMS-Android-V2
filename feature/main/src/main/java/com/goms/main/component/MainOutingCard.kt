package com.goms.main.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.goms.design_system.R
import com.goms.design_system.component.clickable.gomsClickable
import com.goms.design_system.component.shimmer.shimmerEffect
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.theme.GomsTheme.typography
import com.goms.main.data.OutingData
import com.goms.main.data.toData
import com.goms.main.viewmodel.GetOutingCountUiState
import com.goms.main.viewmodel.GetOutingListUiState
import com.goms.model.enum.Authority
import com.goms.ui.toText

@Composable
fun MainOutingCard(
    modifier: Modifier = Modifier,
    role: Authority,
    getOutingListUiState: GetOutingListUiState,
    getOutingCountUiState: GetOutingCountUiState,
    onErrorToast: (throwable: Throwable?, message: String?) -> Unit,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "외출 현황",
                    style = typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = colors.WHITE
                )
                when (getOutingCountUiState) {
                    GetOutingCountUiState.Loading -> {
                        Box(
                            modifier = Modifier
                                .size(56.dp, 23.dp)
                                .shimmerEffect(color = colors.WHITE)
                        )
                    }

                    is GetOutingCountUiState.Error -> {
                        onErrorToast(getOutingCountUiState.exception, "외출학생 숫자를 가져오지 못했습니다")
                    }

                    GetOutingCountUiState.Empty -> {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "0",
                                style = typography.textMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = if (role == Authority.ROLE_STUDENT_COUNCIL) colors.A7 else colors.P5
                            )
                            Text(
                                text = "명이 외출중",
                                style = typography.textMedium,
                                fontWeight = FontWeight.Normal,
                                color = colors.G4
                            )
                        }
                    }

                    is GetOutingCountUiState.Success -> {
                        val count = getOutingCountUiState.getOutingCountResponse

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "${count.outingCount}",
                                style = typography.textMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = if (role == Authority.ROLE_STUDENT_COUNCIL) colors.A7 else colors.P5
                            )
                            Text(
                                text = "명이 외출중",
                                style = typography.textMedium,
                                fontWeight = FontWeight.Normal,
                                color = colors.G4
                            )
                        }
                    }
                }
            }
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
                if (role == Authority.ROLE_STUDENT) {
                    Text(
                        modifier = Modifier.gomsClickable { onClick() },
                        text = "더보기",
                        style = typography.buttonSmall,
                        fontWeight = FontWeight.Normal,
                        color = colors.G7
                    )
                } else {
                    Text(
                        modifier = Modifier.gomsClickable { onClick() },
                        text = "인원 관리하기",
                        style = typography.buttonSmall,
                        fontWeight = FontWeight.Normal,
                        color = colors.G7
                    )
                }
            }
        }
        if (getOutingCountUiState is GetOutingCountUiState.Success) {
            when (getOutingListUiState) {
                GetOutingListUiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .size(56.dp, 23.dp)
                            .shimmerEffect(color = colors.WHITE)
                    )
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 10000.dp)
                    ) {
                        items(5) {
                            ShimmerMainOutingItem(modifier = modifier)
                        }
                    }
                }

                is GetOutingListUiState.Error -> {
                    onErrorToast(getOutingListUiState.exception, "외출자 정보를 가져오지 못했습니다")
                }

                is GetOutingListUiState.Success -> {
                    val list = getOutingListUiState.getOutingListResponse

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 10000.dp)
                    ) {
                        items(list.size) {
                            MainOutingItem(
                                modifier = Modifier.fillMaxWidth(),
                                data = list[it].toData()
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainOutingItem(
    modifier: Modifier = Modifier,
    data: OutingData
) {
    Row(
        modifier = modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (data.profileUrl.isNullOrEmpty()) {
            Image(
                painter = painterResource(R.drawable.ic_profile),
                contentDescription = "Default Profile Image",
                modifier = Modifier.size(28.dp)
            )
        } else {
            AsyncImage(
                model = data.profileUrl,
                modifier = Modifier
                    .size(28.dp)
                    .clip(RoundedCornerShape(40.dp)),
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
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "${data.grade}기 | ${data.major.toText()}",
            style = typography.caption,
            fontWeight = FontWeight.Normal,
            color = colors.G4
        )
    }
}

@Composable
fun ShimmerMainOutingItem(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .shimmerEffect(color = colors.WHITE)
        )
        Box(
            modifier = Modifier
                .size(56.dp, 23.dp)
                .shimmerEffect(color = colors.WHITE)
        )
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .size(60.dp, 16.dp)
                .shimmerEffect(color = colors.WHITE)
        )
    }
}
