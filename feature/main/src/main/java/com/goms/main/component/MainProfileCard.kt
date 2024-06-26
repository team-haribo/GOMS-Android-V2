package com.goms.main.component

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.goms.main.data.ProfileData
import com.goms.main.data.toData
import com.goms.main.viewmodel.uistate.GetProfileUiState
import com.goms.model.enum.Authority
import com.goms.ui.toText

@Composable
internal fun MainProfileCard(
    modifier: Modifier = Modifier,
    getProfileUiState: GetProfileUiState,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit
) {
    when (getProfileUiState) {
        is GetProfileUiState.Loading -> {
            ShimmerMainProfileCardComponent(modifier = modifier)
        }

        is GetProfileUiState.Success -> {
            val data = getProfileUiState.getProfileResponseModel

            MainProfileCardComponent(
                modifier = modifier,
                data = data.toData()
            )
        }

        is GetProfileUiState.Error -> {
            onErrorToast(getProfileUiState.exception, R.string.error_get_profile)
        }
    }
}

@Composable
private fun MainProfileCardComponent(
    modifier: Modifier,
    data: ProfileData
) {
    val context = LocalContext.current

    val (stateColor, stateText) = when {
        data.isBlackList -> Pair(colors.N5, context.getString(R.string.blacklist))
        data.isOuting -> Pair(colors.P5, context.getString(R.string.outing))
        data.authority == Authority.ROLE_STUDENT_COUNCIL -> Pair(colors.A7, context.getString(R.string.student_council))
        else -> Pair(colors.G4, context.getString(R.string.waiting_out))
    }

    Surface(
        modifier = modifier,
        color = colors.G1,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (data.profileUrl.isNullOrEmpty()) {
                Image(
                    painter = painterResource(com.goms.design_system.R.drawable.ic_profile),
                    contentDescription = stringResource(id = R.string.default_profile_image),
                    modifier = Modifier
                        .size(64.dp)
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
            Column(
                modifier = Modifier.height(56.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = data.name,
                    style = typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.WHITE
                )
                Text(
                    text = "${data.grade}기 | ${data.major.toText()}",
                    style = typography.textMedium,
                    fontWeight = FontWeight.Normal,
                    color = colors.G4
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stateText,
                style = typography.textSmall,
                fontWeight = FontWeight.Bold,
                color = stateColor
            )
        }
    }
}

@Composable
private fun ShimmerMainProfileCardComponent(modifier: Modifier) {
    Surface(
        modifier = modifier,
        color = colors.G1,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .shimmerEffect(color = colors.WHITE)
            )
            Column(
                modifier = Modifier.height(56.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp, 28.dp)
                        .shimmerEffect(color = colors.WHITE)
                )
                Box(
                    modifier = Modifier
                        .size(88.dp, 23.dp)
                        .shimmerEffect(color = colors.WHITE)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .size(92.dp, 28.dp)
                    .shimmerEffect(color = colors.WHITE)
            )
        }
    }
}
