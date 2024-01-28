package com.goms.main.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.goms.design_system.component.modifier.gomsClickable
import com.goms.design_system.theme.GomsTheme
import com.goms.main.viewmodel.GetOutingCountUiState
import com.goms.main.viewmodel.GetOutingListUiState
import com.goms.model.enum.Authority
import com.goms.model.response.outing.OutingResponse

@Composable
fun MainOutingCard(
    modifier: Modifier = Modifier,
    role: Authority,
    getOutingListUiState: GetOutingListUiState,
    getOutingCountUiState: GetOutingCountUiState,
    onClick: () -> Unit
) {
    when (getOutingCountUiState) {
        GetOutingCountUiState.Loading -> Unit
        is GetOutingCountUiState.Error -> Unit
        GetOutingCountUiState.Empty -> {
            GomsTheme { colors, typography ->
                Surface(
                    modifier = modifier,
                    color = colors.G1,
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(width = 1.dp, color = colors.WHITE.copy(0.15f))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "외출 현황",
                                style = typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = colors.WHITE
                            )
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
                        Divider(modifier = Modifier.height(1.dp), color = colors.WHITE.copy(0.15f))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "0",
                                style = typography.textMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = colors.G7
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
        }
        is GetOutingCountUiState.Success -> {
            when (getOutingListUiState) {
                GetOutingListUiState.Loading -> Unit
                is GetOutingListUiState.Error -> Unit
                is GetOutingListUiState.Success -> {
                    val count = getOutingCountUiState.getOutingCountResponse
                    val list = getOutingListUiState.getOutingListResponse

                    GomsTheme { colors, typography ->
                        Surface(
                            modifier = modifier,
                            color = colors.G1,
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(width = 1.dp, color = colors.WHITE.copy(0.15f))
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "외출 현황",
                                        style = typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = colors.WHITE
                                    )
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
                                Divider(modifier = Modifier.height(1.dp), color = colors.WHITE.copy(0.15f))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "${count.outingCount}",
                                        style = typography.textMedium,
                                        fontWeight = FontWeight.SemiBold,
                                        color = colors.G7
                                    )
                                    Text(
                                        text = "명이 외출중",
                                        style = typography.textMedium,
                                        fontWeight = FontWeight.Normal,
                                        color = colors.G4
                                    )
                                }
                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .heightIn(max = 10000.dp)
                                ) {
                                    items(list.size) {
                                        MainOutingItem(
                                            modifier = Modifier.fillMaxWidth(),
                                            list = list[it]
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
}

@Composable
fun MainOutingItem(
    modifier: Modifier = Modifier,
    list: OutingResponse
) {
    GomsTheme { colors, typography ->
        Row(
            modifier = modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = list.profileUrl,
                modifier = Modifier.size(28.dp),
                contentScale = ContentScale.Crop,
                contentDescription = "Profile Image",
            )
            Text(
                text = list.name,
                style = typography.textMedium,
                fontWeight = FontWeight.SemiBold,
                color = colors.G7
            )
            Text(
                text = "${list.grade}기 | ${list.major}",
                style = typography.caption,
                fontWeight = FontWeight.Normal,
                color = colors.G4
            )
        }
    }
}