package com.goms.main.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.goms.design_system.R
import com.goms.design_system.icon.DeleteIcon
import com.goms.design_system.theme.GomsTheme
import com.goms.design_system.util.formatTime
import com.goms.main.viewmodel.GetOutingCountUiState
import com.goms.main.viewmodel.GetOutingListUiState
import com.goms.main.viewmodel.OutingSearchUiState
import com.goms.model.enum.Authority
import com.goms.model.response.outing.OutingResponse
import com.goms.ui.toText
import java.util.UUID

@Composable
fun OutingStatusList(
    modifier: Modifier = Modifier,
    role: Authority,
    getOutingListUiState: GetOutingListUiState,
    getOutingCountUiState: GetOutingCountUiState,
    outingSearchUiState: OutingSearchUiState,
    onClick: (UUID) -> Unit
) {
    when (getOutingCountUiState) {
        GetOutingCountUiState.Loading -> Unit
        is GetOutingCountUiState.Error -> Unit
        GetOutingCountUiState.Empty -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 100.dp)
            ) {
                OutingListEmptyText()
            }
        }

        is GetOutingCountUiState.Success -> {
            when (outingSearchUiState) {
                OutingSearchUiState.Loading -> Unit
                is OutingSearchUiState.Error -> Unit
                OutingSearchUiState.QueryEmpty -> {
                    when (getOutingListUiState) {
                        GetOutingListUiState.Loading -> Unit
                        is GetOutingListUiState.Error -> Unit
                        is GetOutingListUiState.Success -> {
                            val list = getOutingListUiState.getOutingListResponse

                            GomsTheme { colors, typography ->
                                Column(modifier = modifier.fillMaxWidth()) {
                                    SearchResultText(
                                        modifier = Modifier
                                            .align(Alignment.Start)
                                            .height(40.dp)
                                    )
                                    LazyColumn(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .heightIn(max = 10000.dp)
                                    ) {
                                        items(list.size) {
                                            OutingStatusListItem(
                                                modifier = Modifier.fillMaxWidth(),
                                                role = role,
                                                list = list[it],
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
                        }
                    }
                }

                OutingSearchUiState.Empty -> {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(60.dp)
                    ) {
                        SearchResultText(
                            modifier = Modifier
                                .align(Alignment.Start)
                                .height(40.dp)
                        )
                        SearchEmptyText()
                    }
                }

                is OutingSearchUiState.Success -> {
                    val list = outingSearchUiState.outingSearchResponse

                    GomsTheme { colors, typography ->
                        Column(modifier = modifier.fillMaxWidth()) {
                            SearchResultText(
                                modifier = Modifier
                                    .align(Alignment.Start)
                                    .height(40.dp)
                            )
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(max = 10000.dp)
                            ) {
                                items(list.size) {
                                    OutingStatusListItem(
                                        modifier = Modifier.fillMaxWidth(),
                                        role = role,
                                        list = list[it],
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
                }
            }
        }
    }
}

@Composable
fun OutingStatusListItem(
    modifier: Modifier = Modifier,
    role: Authority,
    list: OutingResponse,
    onClick: (UUID) -> Unit
) {
    GomsTheme { colors, typography ->
        Row(
            modifier = modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (list.profileUrl.isNullOrEmpty()) {
                Image(
                    painter = painterResource(R.drawable.ic_profile),
                    contentDescription = "Default Profile Image",
                    modifier = Modifier.size(48.dp)
                )
            } else {
                AsyncImage(
                    model = list.profileUrl,
                    modifier = Modifier.size(48.dp),
                    contentScale = ContentScale.Crop,
                    contentDescription = "Profile Image",
                )
            }
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = list.name,
                    style = typography.textMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.G7
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${list.grade}기 | ${list.major.toText()}",
                        style = typography.caption,
                        fontWeight = FontWeight.Normal,
                        color = colors.G4
                    )
                    Divider(
                        modifier = Modifier.size(1.dp, 8.dp),
                        color = colors.WHITE.copy(0.15f)
                    )
                    Text(
                        text = "${list.createdTime.formatTime()}에 외출",
                        style = typography.caption,
                        fontWeight = FontWeight.Normal,
                        color = colors.G4
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            if (role == Authority.ROLE_STUDENT_COUNCIL) {
                IconButton(onClick = { onClick(UUID.fromString(list.accountIdx)) }) {
                    DeleteIcon()
                }
            }
        }
    }
}