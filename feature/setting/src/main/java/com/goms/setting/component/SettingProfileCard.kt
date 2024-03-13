package com.goms.setting.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColor
import coil.compose.AsyncImage
import com.goms.design_system.R
import com.goms.design_system.component.clickable.gomsClickable
import com.goms.design_system.component.shimmer.shimmerEffect
import com.goms.design_system.theme.GomsTheme
import com.goms.design_system.util.shadow
import com.goms.model.response.account.ProfileResponse
import com.goms.setting.viewmodel.GetProfileUiState
import com.goms.ui.toText

@Composable
fun SettingProfileCard(
    modifier: Modifier = Modifier,
    onProfileClick: () -> Unit,
    getProfileUiState: GetProfileUiState,
) {
    when (getProfileUiState) {
        GetProfileUiState.Loading -> {
            ShimmerSettingProfileCardComponent(Modifier.padding(20.dp))
        }
        is GetProfileUiState.Success -> {
            val data = getProfileUiState.getProfileResponse

            SettingProfileCardComponent(
                modifier = modifier,
                onProfileClick = onProfileClick,
                data = data
            )
        }
        is GetProfileUiState.Error -> Unit
    }
}

@Composable
fun SettingProfileCardComponent(
    modifier: Modifier,
    onProfileClick: () -> Unit,
    data: ProfileResponse
) {
    GomsTheme { colors, typography ->
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                contentAlignment = Alignment.BottomEnd,
                modifier = Modifier.gomsClickable {
                    onProfileClick()
                }
            ) {
                if (data.profileUrl.isNullOrEmpty()) {
                    Image(
                        painter = painterResource(R.drawable.ic_profile),
                        contentDescription = "Default Profile Image",
                        modifier = Modifier.size(64.dp)
                    )
                } else {
                    AsyncImage(
                        model = data.profileUrl,
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(40.dp)),
                        contentScale = ContentScale.Crop,
                        contentDescription = "Profile Image",
                    )
                }
                Image(
                    modifier = Modifier
                        .clip(RoundedCornerShape(40.dp))
                        .shadow(
                            color = Color(0x14000000)
                                .toArgb()
                                .toColor(),
                            offsetX = 4.dp,
                            offsetY = 4.dp,
                            blurRadius = 8.dp
                        ),
                    painter = painterResource(id = R.drawable.ic_change_profile),
                    contentDescription = "profile change button"
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = data.name,
                    style = typography.titleSmall,
                    color = colors.WHITE,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${data.grade}기 | ${data.major.toText()}",
                    style = typography.textMedium,
                    color = colors.G4,
                    fontWeight = FontWeight.Normal
                )
            }
            Spacer(modifier = Modifier.width(122.dp))
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "지각 횟수",
                    style = typography.textMedium,
                    color = colors.G4,
                    fontWeight = FontWeight.Normal
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Text(
                        text = data.lateCount.toString(),
                        style = typography.titleSmall,
                        color = if(data.lateCount == 0) colors.WHITE else colors.N5,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "번",
                        style = typography.titleSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
fun ShimmerSettingProfileCardComponent(
    modifier: Modifier,
) {
    GomsTheme { colors, typography ->
        Row(
            modifier = modifier.fillMaxWidth()
        ) {
            Box(
                contentAlignment = Alignment.BottomEnd
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .shimmerEffect(color = colors.WHITE)
                )
                Image(
                    modifier = Modifier
                        .clip(RoundedCornerShape(40.dp))
                        .shadow(
                            color = Color(0x14000000)
                                .toArgb()
                                .toColor(),
                            offsetX = 4.dp,
                            offsetY = 4.dp,
                            blurRadius = 8.dp
                        ),
                    painter = painterResource(id = R.drawable.ic_change_profile),
                    contentDescription = "profile change button"
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Box(
                    modifier = Modifier
                        .size(50.dp, 28.dp)
                        .shimmerEffect(
                            color = colors.WHITE,
                            shape = RoundedCornerShape(4.dp)
                        )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .size(64.dp, 24.dp)
                        .shimmerEffect(
                            color = colors.WHITE,
                            shape = RoundedCornerShape(4.dp)
                        )
                )
            }
            Spacer(modifier = Modifier.width(122.dp))
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "지각 횟수",
                    style = typography.textMedium,
                    color = colors.G4,
                    fontWeight = FontWeight.Normal
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row {
                    Box(
                        modifier = Modifier
                            .size(24.dp, 28.dp)
                            .shimmerEffect(
                                color = colors.WHITE,
                                shape = RoundedCornerShape(4.dp)
                            )
                    )
                    Text(
                        text = "번",
                        style = typography.titleSmall,
                        color = colors.WHITE,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}