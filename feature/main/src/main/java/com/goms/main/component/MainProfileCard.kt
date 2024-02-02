package com.goms.main.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.goms.design_system.theme.GomsTheme
import com.goms.design_system.R
import com.goms.main.viewmodel.GetProfileUiState
import com.goms.model.enum.Authority
import com.goms.ui.toText

@Composable
fun MainProfileCard(
    modifier: Modifier = Modifier,
    role: Authority,
    getProfileUiState: GetProfileUiState,
) {
    when (getProfileUiState) {
        GetProfileUiState.Loading -> Unit
        is GetProfileUiState.Error -> Unit
        is GetProfileUiState.Success -> {
            val data = getProfileUiState.getProfileResponse

            GomsTheme { colors, typography ->
                val stateColor = when (role) {
                    Authority.ROLE_STUDENT -> {
                        if (data.isBlackList) {
                            colors.N5
                        } else {
                            if (data.isOuting) {
                                colors.P5
                            } else {
                                colors.G4
                            }
                        }
                    }
                    Authority.ROLE_STUDENT_COUNCIL -> colors.A7
                }

                val stateText = when (role) {
                    Authority.ROLE_STUDENT -> {
                        if (data.isBlackList) {
                            "외출 금지"
                        } else {
                            if (data.isOuting) {
                                "외출 중"
                            } else {
                                "외출 대기 중"
                            }
                        }
                    }
                    Authority.ROLE_STUDENT_COUNCIL -> "학생회"
                }

                Surface(
                    modifier = modifier,
                    color = colors.G1,
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(width = 1.dp, color = colors.WHITE.copy(0.15f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
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
                                modifier = Modifier.size(64.dp),
                                contentScale = ContentScale.Crop,
                                contentDescription = "Profile Image",
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
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
                            style = typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = stateColor
                        )
                    }
                }
            }
        }
    }
}