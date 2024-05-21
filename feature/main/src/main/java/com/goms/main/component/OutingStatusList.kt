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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
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
import com.goms.design_system.component.shimmer.shimmerEffect
import com.goms.design_system.icon.DeleteIcon
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.theme.GomsTheme.typography
import com.goms.design_system.util.formatTime
import com.goms.main.data.OutingData
import com.goms.main.data.toData
import com.goms.main.viewmodel.uistate.GetOutingCountUiState
import com.goms.main.viewmodel.uistate.GetOutingListUiState
import com.goms.main.viewmodel.uistate.OutingSearchUiState
import com.goms.model.enum.Authority
import com.goms.ui.toText
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import java.util.UUID

@Composable
internal fun OutingStatusList(
    modifier: Modifier = Modifier,
    role: Authority,
    getOutingListUiState: GetOutingListUiState,
    getOutingCountUiState: GetOutingCountUiState,
    outingSearchUiState: OutingSearchUiState,
    onErrorToast: (throwable: Throwable?, message: String?) -> Unit,
    onClick: (UUID) -> Unit
) {
    when (getOutingCountUiState) {
        GetOutingCountUiState.Loading -> {
            ShimmerOutingStatusListComponent(modifier = modifier)
        }

        is GetOutingCountUiState.Error -> {
            onErrorToast(getOutingCountUiState.exception, "외출학생 숫자를 가져오지 못했습니다")
        }

        GetOutingCountUiState.Empty -> {
            Column(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.height(240.dp))
                OutingListEmptyText()
            }
        }

        is GetOutingCountUiState.Success -> {
            when (outingSearchUiState) {
                OutingSearchUiState.Loading -> {
                    ShimmerOutingStatusListComponent(modifier = modifier)
                }

                is OutingSearchUiState.Error -> {
                    onErrorToast(outingSearchUiState.exception, "외출자 검색이 실패했습니다")
                }

                OutingSearchUiState.QueryEmpty -> {
                    when (getOutingListUiState) {
                        GetOutingListUiState.Loading -> {
                            ShimmerOutingStatusListComponent(modifier = modifier)
                        }

                        is GetOutingListUiState.Error -> {
                            onErrorToast(getOutingListUiState.exception, "외출자 정보를 가져오지 못했습니다")
                        }

                        is GetOutingListUiState.Success -> {
                            val list = getOutingListUiState.getOutingListResponse

                            OutingStatusListComponent(
                                modifier = modifier,
                                role = role,
                                list = list.map { it.toData() }.toPersistentList(),
                                onClick = onClick
                            )
                        }
                    }
                }

                OutingSearchUiState.Empty -> {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(215.dp)
                    ) {
                        SearchResultText(modifier = Modifier)
                        SearchEmptyText()
                    }
                }

                is OutingSearchUiState.Success -> {
                    val list = outingSearchUiState.outingSearchResponse

                    OutingStatusListComponent(
                        modifier = modifier,
                        role = role,
                        list = list.map { it.toData() }.toPersistentList(),
                        onClick = onClick
                    )
                }
            }
        }
    }
}

@Composable
private fun OutingStatusListComponent(
    modifier: Modifier,
    role: Authority,
    list: PersistentList<OutingData>,
    onClick: (UUID) -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        SearchResultText(modifier = Modifier)
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 10000.dp)
        ) {
            items(list.size) {
                OutingStatusListItem(
                    modifier = Modifier.fillMaxWidth(),
                    role = role,
                    data = list[it],
                    onClick = onClick
                )
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = colors.WHITE.copy(0.15f)
                )
            }
        }
    }
}


@Composable
private fun OutingStatusListItem(
    modifier: Modifier = Modifier,
    role: Authority,
    data: OutingData,
    onClick: (UUID) -> Unit
) {
    Row(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (data.profileUrl.isNullOrEmpty()) {
            Image(
                painter = painterResource(R.drawable.ic_profile),
                contentDescription = "Default Profile Image",
                modifier = Modifier.size(48.dp)
            )
        } else {
            AsyncImage(
                model = data.profileUrl,
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(40.dp)),
                contentScale = ContentScale.Crop,
                contentDescription = "Profile Image",
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = data.name,
                style = typography.textMedium,
                fontWeight = FontWeight.SemiBold,
                color = colors.G7
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${data.grade}기 | ${data.major.toText()}",
                    style = typography.caption,
                    fontWeight = FontWeight.Normal,
                    color = colors.G4
                )
                Divider(
                    modifier = Modifier.size(1.dp, 8.dp),
                    color = colors.WHITE.copy(0.15f)
                )
                Text(
                    text = "${data.createdTime.formatTime()}에 외출",
                    style = typography.caption,
                    fontWeight = FontWeight.Normal,
                    color = colors.G4
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        if (role == Authority.ROLE_STUDENT_COUNCIL) {
            IconButton(onClick = { onClick(UUID.fromString(data.accountIdx)) }) {
                DeleteIcon()
            }
        }
    }
}


@Composable
private fun ShimmerOutingStatusListComponent(modifier: Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        SearchResultText(modifier = Modifier)
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 10000.dp)
        ) {
            items(10) {
                ShimmerOutingStatusListItem(modifier = modifier)
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = colors.WHITE.copy(0.15f)
                )
            }
        }
    }
}


@Composable
private fun ShimmerOutingStatusListItem(modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .shimmerEffect(color = colors.WHITE)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Box(
                modifier = Modifier
                    .size(42.dp, 18.dp)
                    .shimmerEffect(color = colors.WHITE)
            )
            Box(
                modifier = Modifier
                    .size(120.dp, 14.dp)
                    .shimmerEffect(color = colors.WHITE)
            )
        }
    }
}
