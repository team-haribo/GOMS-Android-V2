package com.goms.main.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.goms.design_system.component.shimmer.shimmerEffect
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.theme.GomsTheme.typography
import com.goms.main.R
import com.goms.main.data.ProfileData
import com.goms.main.data.toData
import com.goms.main.viewmodel.uistate.GetProfileUiState
import com.goms.model.enum.Authority
import com.goms.ui.toText
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
internal fun MainTimeProfileCard(
    modifier: Modifier = Modifier,
    time: Date,
    getProfileUiState: GetProfileUiState,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit
) {
    when (getProfileUiState) {
        GetProfileUiState.Loading -> {
            ShimmerMainTimeProfileCardComponent(modifier = modifier)
        }

        is GetProfileUiState.Success -> {
            val data = getProfileUiState.getProfileResponseModel

            MainTimeProfileCardComponent(
                modifier = modifier,
                data = data.toData(),
                time = time
            )
        }

        is GetProfileUiState.Error -> {
            onErrorToast(getProfileUiState.exception, R.string.error_get_profile)
        }
    }
}

@Composable
private fun MainTimeProfileCardComponent(
    modifier: Modifier,
    data: ProfileData,
    time: Date
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
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
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
            Column(
                modifier = Modifier
                    .height(64.dp)
                    .padding(vertical = 4.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stateText,
                    style = typography.textMedium,
                    fontWeight = FontWeight.Bold,
                    color = stateColor
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = SimpleDateFormat("aa", Locale.ENGLISH).format(time).toString(),
                        style = typography.textLarge,
                        fontWeight = FontWeight.Bold,
                        color = colors.G4
                    )
                    Text(
                        text = SimpleDateFormat("HH : mm : ss").format(time).toString(),
                        style = typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = colors.G4
                    )
                }
            }
        }
    }
}

@Composable
private fun ShimmerMainTimeProfileCardComponent(modifier: Modifier) {
    Surface(
        modifier = modifier,
        color = colors.G1,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
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
            Column(
                modifier = Modifier
                    .height(64.dp)
                    .padding(vertical = 4.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .size(88.dp, 23.dp)
                        .shimmerEffect(color = colors.WHITE)
                )
                Box(
                    modifier = Modifier
                        .size(150.dp, 28.dp)
                        .shimmerEffect(color = colors.WHITE)
                )
            }
        }
    }
}
