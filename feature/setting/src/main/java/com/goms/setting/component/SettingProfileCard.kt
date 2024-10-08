package com.goms.setting.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColor
import coil.compose.AsyncImage
import com.goms.design_system.component.clickable.gomsClickable
import com.goms.design_system.component.shimmer.shimmerEffect
import com.goms.design_system.component.spacer.GomsSpacer
import com.goms.design_system.component.spacer.SpacerSize
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.theme.GomsTheme.typography
import com.goms.design_system.util.shadow
import com.goms.model.enum.Authority
import com.goms.setting.R
import com.goms.setting.data.ProfileData
import com.goms.setting.data.toData
import com.goms.setting.viewmodel.uistate.GetProfileUiState
import com.goms.ui.toText

@Composable
internal fun SettingProfileCard(
    modifier: Modifier = Modifier,
    role: String,
    onProfileClick: () -> Unit,
    getProfileUiState: GetProfileUiState,
) {
    when (getProfileUiState) {
        GetProfileUiState.Loading -> {
            ShimmerSettingProfileCardComponent(modifier = Modifier)
        }

        is GetProfileUiState.Success -> {
            val data = getProfileUiState.getProfileResponseModel

            SettingProfileCardComponent(
                modifier = modifier,
                role = role,
                onProfileClick = onProfileClick,
                data = data.toData()
            )
        }

        is GetProfileUiState.Error -> Unit
    }
}

@Composable
private fun SettingProfileCardComponent(
    modifier: Modifier,
    role: String,
    onProfileClick: () -> Unit,
    data: ProfileData
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            contentAlignment = Alignment.BottomEnd,
            modifier = Modifier.gomsClickable {
                onProfileClick()
            }
        ) {
            if (data.profileUrl.isNullOrEmpty()) {
                Image(
                    painter = painterResource(com.goms.design_system.R.drawable.ic_profile),
                    contentDescription = stringResource(id = R.string.default_profile_image),
                    modifier = Modifier.size(64.dp)
                )
            } else {
                AsyncImage(
                    model = data.profileUrl,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(40.dp)),
                    contentScale = ContentScale.Crop,
                    contentDescription = stringResource(id = R.string.profile_image),
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
                painter = painterResource(
                    id = if (role == Authority.ROLE_STUDENT_COUNCIL.name) com.goms.design_system.R.drawable.ic_change_profile_admin
                    else com.goms.design_system.R.drawable.ic_change_profile
                ),
                contentDescription = stringResource(id = R.string.profile_change_button)
            )
        }
        Column {
            Text(
                text = data.name,
                style = typography.titleSmall,
                color = colors.WHITE,
                fontWeight = FontWeight.SemiBold
            )
            GomsSpacer(size = SpacerSize.Tiny)
            Text(
                text = "${data.grade}기 | ${data.major.toText()}",
                style = typography.textMedium,
                color = colors.G4,
                fontWeight = FontWeight.Normal
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = stringResource(id = R.string.late_count),
                style = typography.textMedium,
                color = colors.G4,
                fontWeight = FontWeight.Normal
            )
            GomsSpacer(size = SpacerSize.Tiny)
            Row {
                Text(
                    text = data.lateCount.toString(),
                    style = typography.titleSmall,
                    color = if (data.lateCount == 0) colors.WHITE else colors.N5,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = stringResource(id = R.string.number),
                    color = colors.WHITE,
                    style = typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun ShimmerSettingProfileCardComponent(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
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
                painter = painterResource(id = com.goms.design_system.R.drawable.ic_change_profile),
                contentDescription = stringResource(id = R.string.profile_change_button)
            )
        }
        Column {
            Box(
                modifier = Modifier
                    .size(50.dp, 24.dp)
                    .shimmerEffect(color = colors.WHITE)
            )
            GomsSpacer(size = SpacerSize.ExtraSmall)
            Box(
                modifier = Modifier
                    .size(64.dp, 20.dp)
                    .shimmerEffect(color = colors.WHITE,)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = stringResource(id = R.string.late_count),
                style = typography.textMedium,
                color = colors.G4,
                fontWeight = FontWeight.Normal
            )
            GomsSpacer(size = SpacerSize.Tiny)
            Row {
                Box(
                    modifier = Modifier
                        .size(40.dp, 25.dp)
                        .shimmerEffect(color = colors.WHITE)
                )
            }
        }
    }
}