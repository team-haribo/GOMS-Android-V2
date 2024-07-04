package com.goms.main.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.goms.design_system.component.shimmer.shimmerEffect
import com.goms.design_system.component.spacer.GomsSpacer
import com.goms.design_system.component.spacer.SpacerSize
import com.goms.design_system.icon.WriteIcon
import com.goms.design_system.theme.GomsTheme.colors
import com.goms.design_system.theme.GomsTheme.typography
import com.goms.main.R
import com.goms.main.data.StudentData
import com.goms.main.data.toData
import com.goms.main.viewmodel.uistate.GetStudentListUiState
import com.goms.main.viewmodel.uistate.StudentSearchUiState
import com.goms.model.enum.Authority
import com.goms.model.enum.BlackList
import com.goms.ui.toText
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import java.util.UUID

@Composable
internal fun StudentManagementList(
    modifier: Modifier = Modifier,
    getStudentListUiState: GetStudentListUiState,
    studentSearchUiState: StudentSearchUiState,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
    onBottomSheetOpenClick: () -> Unit,
    onClick: (UUID, String, String) -> Unit
) {
    when (studentSearchUiState) {
        StudentSearchUiState.Loading -> {
            ShimmerStudentManagementListComponent(modifier = modifier)
        }

        is StudentSearchUiState.Error -> {
            onErrorToast(studentSearchUiState.exception, R.string.error_student_search)
        }

        StudentSearchUiState.QueryEmpty -> {
            when (getStudentListUiState) {
                GetStudentListUiState.Loading -> {
                    ShimmerStudentManagementListComponent(modifier = modifier)
                }

                is GetStudentListUiState.Error -> {
                    onErrorToast(getStudentListUiState.exception, R.string.error_get_student_list)
                }

                is GetStudentListUiState.Success -> {
                    val list = getStudentListUiState.getStudentResponseModel

                    StudentManagementListComponent(
                        modifier = modifier,
                        list = list.map { it.toData() }.toPersistentList(),
                        onBottomSheetOpenClick = onBottomSheetOpenClick,
                        onClick = onClick
                    )
                }
            }
        }

        StudentSearchUiState.Empty -> {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(200.dp)
            ) {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SearchResultText(modifier = Modifier)
                    FilterText(onFilterTextClick = onBottomSheetOpenClick)
                }
                SearchEmptyText()
            }
        }

        is StudentSearchUiState.Success -> {
            val list = studentSearchUiState.studentSearchResponse

            StudentManagementListComponent(
                modifier = modifier,
                list = list.map { it.toData() }.toPersistentList(),
                onBottomSheetOpenClick = onBottomSheetOpenClick,
                onClick = onClick
            )
        }
    }
}

@Composable
private fun StudentManagementListComponent(
    modifier: Modifier,
    list: PersistentList<StudentData>,
    onBottomSheetOpenClick: () -> Unit,
    onClick: (UUID, String, String) -> Unit
) {
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
            items(
                items = list,
                key = { data ->
                    data.accountIdx
                }
            ) { data ->
                StudentManagementListItem(
                    modifier = Modifier.fillMaxWidth(),
                    data = data,
                    onClick = onClick
                )
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    color = colors.WHITE.copy(0.15f)
                )
            }
        }
    }
}

@Composable
private fun StudentManagementListItem(
    modifier: Modifier = Modifier,
    data: StudentData,
    onClick: (UUID, String, String) -> Unit
) {
    Row(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (data.profileUrl.isNullOrEmpty()) {
            Image(
                painter = painterResource(com.goms.design_system.R.drawable.ic_profile),
                contentDescription = stringResource(id = R.string.default_profile_image),
                modifier = Modifier
                    .size(48.dp)
                    .border(
                        width = 4.dp,
                        color = when {
                            data.isBlackList -> colors.N5
                            data.authority == Authority.ROLE_STUDENT_COUNCIL -> colors.A7
                            else -> Color.Transparent
                        },
                        shape = CircleShape
                    )
            )
        } else {
            AsyncImage(
                model = data.profileUrl,
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(40.dp))
                    .border(
                        width = 4.dp,
                        color = when {
                            data.isBlackList -> colors.N5
                            data.authority == Authority.ROLE_STUDENT_COUNCIL -> colors.A7
                            else -> Color.Transparent
                        },
                        shape = CircleShape
                    ),
                contentScale = ContentScale.Crop,
                contentDescription = stringResource(id = R.string.profile_image),
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = data.name,
                style = typography.textMedium,
                fontWeight = FontWeight.SemiBold,
                color = when {
                    data.isBlackList -> colors.N5
                    data.authority == Authority.ROLE_STUDENT_COUNCIL -> colors.A7
                    else -> colors.G7
                },
            )
            Text(
                text = "${data.grade}기 | ${data.major.toText()}",
                style = typography.caption,
                fontWeight = FontWeight.Normal,
                color = colors.G4
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = {
            onClick(
                UUID.fromString(data.accountIdx),
                if (data.isBlackList) BlackList.BLACK_LIST.name else BlackList.NO_BLACK_LIST.name,
                if (data.authority == Authority.ROLE_STUDENT_COUNCIL) Authority.ROLE_STUDENT_COUNCIL.name else Authority.ROLE_STUDENT.name
            )
        }) {
            WriteIcon()
        }
    }
}

@Composable
private fun ShimmerStudentManagementListComponent(modifier: Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SearchResultText(modifier = Modifier)
            FilterText(onFilterTextClick = {})
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 10000.dp)
        ) {
            items(10) {
                ShimmerStudentManagementListItem(modifier = modifier)
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    color = colors.WHITE.copy(0.15f)
                )
            }
        }
    }
}

@Composable
private fun ShimmerStudentManagementListItem(modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .shimmerEffect(color = colors.WHITE)
        )
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
