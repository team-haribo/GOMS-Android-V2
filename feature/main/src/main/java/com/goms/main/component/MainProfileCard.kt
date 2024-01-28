package com.goms.main.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.goms.design_system.theme.GomsTheme
import com.goms.model.enum.Authority

@Composable
fun MainProfileCard(
    modifier: Modifier = Modifier,
    role: Authority
) {
    GomsTheme { colors, typography ->
        val stateColor = when (role) {
            Authority.ROLE_STUDENT -> colors.G4
            Authority.ROLE_STUDENT_COUNCIL -> colors.A7
        }

        val stateText = when (role) {
            Authority.ROLE_STUDENT -> "외출 대기중"
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
                AsyncImage(
                    model = "",
                    modifier = Modifier.size(64.dp),
                    contentScale = ContentScale.Crop,
                    contentDescription = "Profile Image",
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(verticalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = "홍길동",
                        style = typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = colors.WHITE
                    )
                    Text(
                        text = "7기 | SW개발",
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

@Composable
@Preview(showBackground = true)
fun MainProfileCardPreview() {
    Column {
        MainProfileCard(role = Authority.ROLE_STUDENT)
        MainProfileCard(role = Authority.ROLE_STUDENT_COUNCIL)
    }
}