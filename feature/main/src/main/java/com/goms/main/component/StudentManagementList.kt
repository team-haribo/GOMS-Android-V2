package com.goms.main.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.goms.design_system.R
import com.goms.design_system.icon.WriteIcon
import com.goms.design_system.theme.GomsTheme
import com.goms.main.viewmodel.GetStudentListUiState
import com.goms.model.enum.Authority
import com.goms.model.enum.Status
import com.goms.model.response.council.StudentResponse
import com.goms.model.response.outing.OutingResponse
import com.goms.ui.toText
import java.util.UUID

@Composable
fun StudentManagementList(
    modifier: Modifier = Modifier,
    getStudentListUiState: GetStudentListUiState,
    onBottomSheetOpenClick: () -> Unit,
    onClick: (UUID, String) -> Unit
) {
    when (getStudentListUiState) {
        GetStudentListUiState.Loading -> Unit
        is GetStudentListUiState.Error -> Unit
        is GetStudentListUiState.Success -> {
            val list = getStudentListUiState.getStudentResponse

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
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 10000.dp)
                    ) {
                        items(list.size) {
                            StudentManagementListItem(
                                modifier = Modifier.fillMaxWidth(),
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

@Composable
fun StudentManagementListItem(
    modifier: Modifier = Modifier,
    list: StudentResponse,
    onClick: (UUID, String) -> Unit
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
                    modifier = Modifier
                        .size(48.dp)
                        .border(
                            width = 4.dp,
                            color = if (list.authority == Authority.ROLE_STUDENT_COUNCIL) colors.A7
                            else if (list.isBlackList) colors.N5
                            else Color.Transparent,
                            shape = CircleShape
                        )
                )
            } else {
                AsyncImage(
                    model = list.profileUrl,
                    modifier = Modifier
                        .size(48.dp)
                        .border(
                            width = 4.dp,
                            color = if (list.authority == Authority.ROLE_STUDENT_COUNCIL) colors.A7
                            else if (list.isBlackList) colors.N5
                            else Color.Transparent,
                            shape = CircleShape
                        ),
                    contentScale = ContentScale.Crop,
                    contentDescription = "Profile Image",
                )
            }
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = list.name,
                    style = typography.textMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = if (list.authority == Authority.ROLE_STUDENT_COUNCIL) colors.A7
                    else if (list.isBlackList) colors.N5
                    else colors.G7
                )
                Text(
                    text = "${list.grade}기 | ${list.major.toText()}",
                    style = typography.caption,
                    fontWeight = FontWeight.Normal,
                    color = colors.G4
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = {
                onClick(
                    UUID.fromString(list.accountIdx),
                    if (list.authority == Authority.ROLE_STUDENT_COUNCIL) Status.ROLE_STUDENT_COUNCIL.value
                    else if (list.isBlackList) Status.BLACK_LIST.value
                    else Status.ROLE_STUDENT.value
                )
            }) {
                WriteIcon()
            }
        }
    }
}