package com.mommydndn.app.ui.care

import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mommydndn.app.R
import com.mommydndn.app.domain.model.NearbyNeighborhoodDistance
import com.mommydndn.app.domain.model.CareType
import com.mommydndn.app.domain.model.WorkPeriod
import com.mommydndn.app.ui.care.components.filter.FilterChip
import com.mommydndn.app.ui.care.components.post.TopAppBarHeight
import com.mommydndn.app.ui.care.filter.CareFilters
import com.mommydndn.app.ui.care.filter.CareOrderBy
import com.mommydndn.app.ui.care.filter.CareTypesFilter
import com.mommydndn.app.ui.care.filter.DaysOfWeekFilter
import com.mommydndn.app.ui.care.filter.NeighborhoodFilter
import com.mommydndn.app.ui.care.filter.WorkHoursFilter
import com.mommydndn.app.ui.care.filter.WorkPeriodFilter
import com.mommydndn.app.ui.care.job.CareJobList
import com.mommydndn.app.ui.care.job.CareJobUiModel
import com.mommydndn.app.ui.care.job.mockCareJobUiModels
import com.mommydndn.app.ui.components.tab.MediumTab
import com.mommydndn.app.ui.theme.Grey700
import com.mommydndn.app.ui.theme.GreyOpacity400
import com.mommydndn.app.ui.theme.heading600
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalTime

@Composable
internal fun Care(
    onNeighborhoodClick: () -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CareViewModel = hiltViewModel(),
) {
    val selectedTabIndex = remember { mutableIntStateOf(0) }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val selectedFilter = remember { mutableStateOf<CareFilters<*>?>(null) }

    val coroutineScope = rememberCoroutineScope()

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        animationSpec = spring(dampingRatio = 0.85f, stiffness = 100f),
        skipHalfExpanded = true,
    )

    @Suppress("NAME_SHADOWING")
    when (val uiState = uiState) {
        CareUiState.Loading -> {

        }
        is CareUiState.Success -> {
            CareContent(
                neighborhood = uiState.neighborhood,
                onNeighborhoodClick = onNeighborhoodClick,
                onSearchClick = onSearchClick,
                selectedTabIndex = selectedTabIndex.intValue,
                onTabSelected = {
                    selectedTabIndex.intValue = it
                },
                orderBy = uiState.order,
                onOrderClick = {},
                filters = uiState.filters,
                onFilterClick = {
                    // todo: recompose twice?
                    selectedFilter.value = it

                    coroutineScope.launch {
                        sheetState.show()
                    }
                },
                jobOpeningListItems = uiState.jobs,
                selectedFilter = selectedFilter.value,
                sheetState = sheetState,
                onSheetCloseClick = {
                    coroutineScope.launch {
                        sheetState.hide()
                    }
                },
                onFilterUpdated = viewModel::setFilter,
                modifier = modifier,
            )
        }
        is CareUiState.Failure -> {

        }
    }
}

data class NeighborhoodUiModel(
    val id: Long,
    val name: String,
    val address: String,
    val nearbyDistance: NearbyNeighborhoodDistance,
)

@Composable
private fun CareContent(
    neighborhood: NeighborhoodUiModel,
    onNeighborhoodClick: () -> Unit,
    onSearchClick: () -> Unit,
    selectedTabIndex: Int,
    onTabSelected: (index: Int) -> Unit,
    orderBy: CareOrderBy,
    onOrderClick: () -> Unit,
    filters: List<CareFilters<*>>,
    onFilterClick: (CareFilters<*>) -> Unit,
    jobOpeningListItems: List<CareJobUiModel>,
    selectedFilter: CareFilters<*>?,
    sheetState: ModalBottomSheetState,
    onSheetCloseClick: () -> Unit,
    onFilterUpdated: (CareFilters<*>) -> Unit,
    modifier: Modifier = Modifier,
) {
    ModalBottomSheetLayout(
        sheetContent = {
            if (selectedFilter != null) { // TODO
                CareFilterModalBottomSheet(
                    selectedFilter = selectedFilter,
                    onCloseClick = onSheetCloseClick,
                    onFilterUpdated = onFilterUpdated,
                )
            }
        },
        sheetState = sheetState,
        sheetBackgroundColor = Color.Transparent,
        sheetContentColor = Color.Transparent,
        scrimColor = GreyOpacity400,
        modifier = modifier,
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxWidth()) {
                CareTopAppBar(
                    neighborhoodName = neighborhood.name,
                    onNeighborhoodClick = onNeighborhoodClick,
                    onSearchClick = onSearchClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(TopAppBarHeight)
                        .padding(horizontal = 20.dp),
                )

                CareTabRow(
                    selectedTabIndex = selectedTabIndex,
                    onTabSelected = onTabSelected,
                    modifier = Modifier.fillMaxWidth(),
                )

                CareOrderAndFilters(
                    orderBy = orderBy,
                    onOrderClick = onOrderClick,
                    filters = filters,
                    onFilterClick = onFilterClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                )

                CareJobList(
                    items = jobOpeningListItems,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}

@Composable
private fun CareTopAppBar(
    neighborhoodName: String, // todo: type
    onNeighborhoodClick: () -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier
                .wrapContentSize()
                .clickable(onClick = onNeighborhoodClick),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = neighborhoodName,
                style = MaterialTheme.typography.heading600.merge(
                    color = Grey700,
                    fontWeight = FontWeight.Bold,
                )
            )

            Icon(
                painter = painterResource(id = R.drawable.icon_arrow_down),
                contentDescription = "CareTopAppBar_Neighborhood_ArrowDown",
                modifier = Modifier.size(36.dp),
                tint = Color.Unspecified,
            )
        }

        Icon(
            painter = painterResource(id = R.drawable.icon_search),
            contentDescription = "CareTopAppBar_Search",
            modifier = Modifier
                .size(36.dp)
                .clickable(onClick = onSearchClick),
            tint = Color.Unspecified
        )
    }
}

@Composable
private fun CareTabRow(
    selectedTabIndex: Int,
    onTabSelected: (index: Int) -> Unit,
    modifier: Modifier = Modifier,
    tabNames: List<String> = listOf(
        stringResource(R.string.job_opening),
        stringResource(R.string.job_hunting),
        stringResource(R.string.authenticated_agency)
    )
) {
    MediumTab(
        tabNames = tabNames,
        selectedTabIndex = selectedTabIndex,
        onTabSelected = onTabSelected,
        modifier = modifier
    )
}

@Composable
private fun CareOrderAndFilters(
    orderBy: CareOrderBy,
    onOrderClick: () -> Unit,
    filters: List<CareFilters<*>>,
    onFilterClick: (CareFilters<*>) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilterChip(
            selected = true,
            text = "orderBy.displayName()",
            onClick = onOrderClick,
            modifier = Modifier.padding(start = 24.dp)
        )

        filters.sortedByDescending { it.selected }.forEachIndexed { index, filter ->
            FilterChip(
                selected = filter.selected,
                text = filter.displayName(),
                onClick = { onFilterClick(filter) },
                modifier = Modifier.apply {
                    if (index == filters.lastIndex) {
                        padding(end = 24.dp)
                    }
                }
            )
        }
    }
}

@Composable
private fun CareFilterModalBottomSheet(
    selectedFilter: CareFilters<*>,
    onCloseClick: () -> Unit,
    onFilterUpdated: (CareFilters<*>) -> Unit,
) {
    when (selectedFilter) {
        is NeighborhoodFilter -> TODO()
        is CareTypesFilter -> TODO()
        is DaysOfWeekFilter -> TODO()
        is WorkHoursFilter -> TODO()
        is WorkPeriodFilter -> TODO()
    }
}

@Preview
@Composable
private fun CareScreenPreview() {
    val fakeNeighborhood = NeighborhoodUiModel(
        id = 0,
        name = "서초동",
        address = "서울 서초구 서초중앙로 15",
        nearbyDistance = NearbyNeighborhoodDistance.DISTANT
    )

    CareContent(
        neighborhood = fakeNeighborhood,
        onNeighborhoodClick = {},
        onSearchClick = {},
        selectedTabIndex = 0,
        onTabSelected = {},
        orderBy = CareOrderBy.LATEST,
        onOrderClick = {},
        filters = buildList {
//            add(NeighborhoodFilter(neighborhood = fakeNeighborhood))
//            add(CareTypesFilter())
//            add(DaysOfWeekFilter())
//            add(WorkHoursFilter())
//            add(WorkPeriodFilter(workPeriod = WorkPeriod.REGULAR))
        },
        onFilterClick = {},
        jobOpeningListItems = mockCareJobUiModels,
        selectedFilter = null,
        sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden),
        onSheetCloseClick = {},
        onFilterUpdated = {},
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    )
}

@Preview
@Composable
private fun CareTypesFilterChipPreview() {
    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        with(CareTypesFilter()) {
            FilterChip(
                selected = selected,
                text = displayName(),
                onClick = {}
            )
        }

        val careTypes = listOf(
            CareType.ChildCare,
            CareType.SchoolTransportation,
            CareType.Housekeeping,
        )

        with(CareTypesFilter(careTypes = careTypes)) {
            FilterChip(
                selected = selected,
                text = displayName(),
                onClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun DaysOfWeekFilterChipPreview() {
    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        with(DaysOfWeekFilter()) {
            FilterChip(
                selected = selected,
                text = displayName(),
                onClick = {}
            )
        }

        val daysOfWeek = listOf(
            DayOfWeek.TUESDAY,
            DayOfWeek.FRIDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.SATURDAY,
        )

        with(DaysOfWeekFilter(daysOfWeek = daysOfWeek)) {
            FilterChip(
                selected = selected,
                text = displayName(),
                onClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun WorkHoursFilterChipPreview() {
    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        with(WorkHoursFilter()) {
            FilterChip(
                selected = selected,
                text = displayName(),
                onClick = {}
            )
        }

        with(
            WorkHoursFilter(
                startTime = LocalTime.of(12, 0),
                endTime = LocalTime.of(18, 30),
            )
        ) {
            FilterChip(
                selected = selected,
                text = displayName(),
                onClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun WorkPeriodFilterChipPreview() {
    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        with(WorkPeriodFilter()) {
            FilterChip(
                selected = selected,
                text = displayName(),
                onClick = {}
            )
        }

        with(WorkPeriodFilter(WorkPeriod.OneTime)) {
            FilterChip(
                selected = selected,
                text = displayName(),
                onClick = {}
            )
        }

        with(WorkPeriodFilter(WorkPeriod.Regular)) {
            FilterChip(
                selected = selected,
                text = displayName(),
                onClick = {}
            )
        }
    }
}