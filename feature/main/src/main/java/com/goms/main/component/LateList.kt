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
import androidx.compose.material3.Divider
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
import com.goms.design_system.component.shimmer.shimmerEffect
import com.goms.design_system.theme.GomsTheme
import com.goms.main.viewmodel.GetLateListUiState
import com.goms.model.response.council.LateResponse
import com.goms.ui.toText

@Composable
fun LateList(
    modifier: Modifier = Modifier,
    getLateListUiState: GetLateListUiState,
    onBottomSheetOpenClick: () -> Unit
) {
    GomsTheme { colors, typography ->
        Column(modifier = modifier.fillMaxWidth()) {
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SearchResultText(modifier = Modifier)
                FilterText(onFilterTextClick = onBottomSheetOpenClick)
            }
            when (getLateListUiState) {
                GetLateListUiState.Loading -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 10000.dp)
                    ) {
                        items(10) {
                            ShimmerLateListItem(modifier = modifier)
                            Divider(
                                modifier = Modifier.fillMaxWidth(),
                                color = colors.WHITE.copy(0.15f)
                            )
                        }
                    }
                }
                is GetLateListUiState.Error -> Unit
                GetLateListUiState.Empty -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 60.dp)
                    ) {
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
                        items(list.size) {
                            LateListItem(
                                modifier = Modifier.fillMaxWidth(),
                                list = list[it]
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

@Composable
fun LateListItem(
    modifier: Modifier = Modifier,
    list: LateResponse
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
                Text(
                    text = "${list.grade}기 | ${list.major.toText()}",
                    style = typography.caption,
                    fontWeight = FontWeight.Normal,
                    color = colors.G4
                )
            }
        }
    }
}

@Composable
fun ShimmerLateListItem(modifier: Modifier) {
    GomsTheme { colors, typography ->
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
                        .size(50.dp, 14.dp)
                        .shimmerEffect(color = colors.WHITE)
                )
            }
        }
    }
}