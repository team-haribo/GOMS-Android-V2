package com.goms.main.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.goms.design_system.component.modifier.gomsClickable
import com.goms.design_system.theme.GomsTheme
import com.goms.model.enum.Authority

@Composable
fun MainLateCard(
    modifier: Modifier = Modifier,
    list: List<String> = listOf("1", "2", "3", "4"),
    role: Authority,
    onClick: () -> Unit
) {
    var componentWidth by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    GomsTheme { colors, typography ->
        Surface(
            modifier = modifier
                .onGloballyPositioned {
                    componentWidth = with(density) {
                        it.size.width.toDp()
                    }
                },
            color = colors.G1,
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(width = 1.dp, color = colors.WHITE.copy(0.15f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "지각자 TOP 3",
                        style = typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = colors.WHITE
                    )
                    if (role == Authority.ROLE_STUDENT_COUNCIL) {
                        Text(
                            modifier = Modifier.gomsClickable { onClick() },
                            text = "더보기",
                            style = typography.buttonSmall,
                            fontWeight = FontWeight.Normal,
                            color = colors.G7
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                if (list.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "\uD83D\uDC4D",
                            fontSize = 80.sp,
                            fontWeight = FontWeight.Bold,
                            color = colors.WHITE
                        )
                        Text(
                            text = "지각자가 없어요! 놀랍게도...",
                            style = typography.textMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = colors.G4
                        )
                    }
                } else {
                    LazyRow(modifier = Modifier.fillMaxWidth()) {
                        items(3) {
                            MainLateItem(
                                modifier = Modifier.widthIn((componentWidth - 32.dp) / 3),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainLateItem(
    modifier: Modifier = Modifier
) {
    GomsTheme { colors, typography ->
        Column(
            modifier = modifier.padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AsyncImage(
                model = "",
                modifier = Modifier.size(56.dp),
                contentScale = ContentScale.Crop,
                contentDescription = "Profile Image",
            )
            Text(
                text = "김경수",
                style = typography.textMedium,
                fontWeight = FontWeight.SemiBold,
                color = colors.G7
            )
            Text(
                text = "7기 | IoT",
                style = typography.caption,
                fontWeight = FontWeight.Normal,
                color = colors.G4
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun MainLateCardPreview() {
    Column {
        MainLateCard(role = Authority.ROLE_STUDENT) {}
        MainLateCard(role = Authority.ROLE_STUDENT_COUNCIL) {}
        MainLateCard(role = Authority.ROLE_STUDENT_COUNCIL, list = emptyList()) {}
    }
}