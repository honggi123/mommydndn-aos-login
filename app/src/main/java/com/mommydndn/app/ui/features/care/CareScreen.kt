package com.mommydndn.app.ui.features.care

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mommydndn.app.R
import com.mommydndn.app.domain.model.care.CareType
import com.mommydndn.app.domain.model.care.WorkPeriod
import com.mommydndn.app.domain.model.user.Neighborhood
import com.mommydndn.app.ui.components.chip.ChipWithBottomArrow
import com.mommydndn.app.ui.components.modal.layout.BaseModalBottomSheetLayout
import com.mommydndn.app.ui.components.tab.MediumTab
import com.mommydndn.app.ui.features.care.filters.CareFilter
import com.mommydndn.app.ui.features.care.filters.CareOrderBy
import com.mommydndn.app.ui.features.care.filters.CareTypesFilter
import com.mommydndn.app.ui.features.care.filters.DaysOfWeekFilter
import com.mommydndn.app.ui.features.care.filters.NeighborhoodsFilter
import com.mommydndn.app.ui.features.care.filters.PayFilter
import com.mommydndn.app.ui.features.care.filters.WorkHoursFilter
import com.mommydndn.app.ui.features.care.filters.WorkPeriodFilter
import com.mommydndn.app.ui.features.care.filters.displayName
import com.mommydndn.app.ui.features.care.filters.hasValue
import com.mommydndn.app.ui.features.care.jobopening.list.CareJobOpeningListFragment
import com.mommydndn.app.ui.features.care.jobopening.list.model.CareJobOpeningListItem
import com.mommydndn.app.ui.features.care.jobopening.list.model.mockCareJobOpeningListItems
import com.mommydndn.app.ui.features.care.modal.NeighborhoodsFilterModalBottomSheet
import com.mommydndn.app.ui.theme.Grey700
import com.mommydndn.app.ui.theme.heading600
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalTime

@Composable
internal fun CareRoute(
    onNeighborhoodClick: () -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CareViewModel = hiltViewModel(),
) {
    val selectedTabIndex = remember { mutableIntStateOf(0) }

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    val selectedFilter = remember { mutableStateOf<CareFilter?>(null) }

    val coroutineScope = rememberCoroutineScope()

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        animationSpec = spring(dampingRatio = 0.85f, stiffness = 100f),
        skipHalfExpanded = true,
    )

    when (@Suppress("NAME_SHADOWING") val uiState = uiState.value) {
        CareUiState.Loading -> {

        }

        is CareUiState.Success -> CareScreen(
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
            jobOpeningListItems = uiState.jobOpeningListItems,
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

        is CareUiState.Failure -> {

        }
    }
}

@Composable
private fun CareScreen(
    neighborhood: Neighborhood,
    onNeighborhoodClick: () -> Unit,
    onSearchClick: () -> Unit,
    selectedTabIndex: Int,
    onTabSelected: (index: Int) -> Unit,
    orderBy: CareOrderBy,
    onOrderClick: () -> Unit,
    filters: List<CareFilter>,
    onFilterClick: (CareFilter) -> Unit,
    jobOpeningListItems: List<CareJobOpeningListItem>,
    selectedFilter: CareFilter?,
    sheetState: ModalBottomSheetState,
    onSheetCloseClick: () -> Unit, // todo: rename?
    onFilterUpdated: (CareFilter) -> Unit,
    modifier: Modifier = Modifier,
) {
    BaseModalBottomSheetLayout(
        sheetContent = {
            if (selectedFilter != null) {
                CareFilterModalBottomSheet(
                    selectedFilter = selectedFilter,
                    onCloseClick = onSheetCloseClick,
                    onFilterUpdated = onFilterUpdated,
                )
            }
        },
        sheetState = sheetState,
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
                        .height(topAppBarHeight)
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

                CareJobOpeningListFragment(
                    items = jobOpeningListItems,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}

// todo: design_system.dimension
internal val topAppBarHeight: Dp = 68.dp

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
    modifier: Modifier = Modifier
) {
    // todo
    val tabNames = listOf(
        stringResource(R.string.job_opening),
        stringResource(R.string.job_hunting),
        stringResource(R.string.authenticated_agency)
    )

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
    filters: List<CareFilter>,
    onFilterClick: (CareFilter) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            ChipWithBottomArrow(
                hasValue = true,
                text = orderBy.displayName(),
                onClick = onOrderClick,
                modifier = Modifier.padding(start = 24.dp)
            )
        }

        itemsIndexed(filters.sortedByDescending { it.hasValue }) { index, filter ->
            ChipWithBottomArrow(
                hasValue = filter.hasValue,
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
    selectedFilter: CareFilter,
    onCloseClick: () -> Unit,
    onFilterUpdated: (CareFilter) -> Unit,
) {
    when (selectedFilter) {
        is NeighborhoodsFilter -> {
            NeighborhoodsFilterModalBottomSheet(
                neighborhoodsFilter = selectedFilter,
                onCloseClick = onCloseClick,
                onUpdateClick = onFilterUpdated,
                modifier = Modifier,
            )
        }

        is CareTypesFilter -> TODO()
        is PayFilter -> TODO()
        is DaysOfWeekFilter -> TODO()
        is WorkHoursFilter -> TODO()
        is WorkPeriodFilter -> TODO()
    }
}

@Preview
@Composable
private fun CareScreenPreview() {
    val fakeNeighborhood = Neighborhood(
        id = 0,
        name = "서초동",
        latitude = 0.0,
        longitude = 0.0,
    )

    CareScreen(
        neighborhood = fakeNeighborhood,
        onNeighborhoodClick = {},
        onSearchClick = {},
        selectedTabIndex = 0,
        onTabSelected = {},
        orderBy = CareOrderBy.LATEST,
        onOrderClick = {},
        filters = buildList {
            add(NeighborhoodsFilter(neighborhood = fakeNeighborhood))
            add(CareTypesFilter())
            add(PayFilter())
            add(DaysOfWeekFilter())
            add(WorkHoursFilter())
            add(WorkPeriodFilter(workPeriod = WorkPeriod.REGULAR))
        },
        onFilterClick = {},
        jobOpeningListItems = mockCareJobOpeningListItems,
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
            ChipWithBottomArrow(
                hasValue = hasValue,
                text = displayName(),
                onClick = {}
            )
        }

        val careTypes = listOf(
            CareType.CHILD_CARE,
            CareType.SCHOOL_TRANSPORTATION,
            CareType.HOUSEKEEPING,
        )

        with(CareTypesFilter(careTypes = careTypes)) {
            ChipWithBottomArrow(
                hasValue = hasValue,
                text = displayName(),
                onClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun PayFilterChipPreview() {
    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        with(PayFilter()) {
            ChipWithBottomArrow(
                hasValue = hasValue,
                text = displayName(),
                onClick = {}
            )
        }

        with(PayFilter(minimum = 15000, maximum = 20000)) {
            ChipWithBottomArrow(
                hasValue = hasValue,
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
            ChipWithBottomArrow(
                hasValue = hasValue,
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
            ChipWithBottomArrow(
                hasValue = hasValue,
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
            ChipWithBottomArrow(
                hasValue = hasValue,
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
            ChipWithBottomArrow(
                hasValue = hasValue,
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
            ChipWithBottomArrow(
                hasValue = hasValue,
                text = displayName(),
                onClick = {}
            )
        }

        with(WorkPeriodFilter(WorkPeriod.ONE_TIME)) {
            ChipWithBottomArrow(
                hasValue = hasValue,
                text = displayName(),
                onClick = {}
            )
        }

        with(WorkPeriodFilter(WorkPeriod.REGULAR)) {
            ChipWithBottomArrow(
                hasValue = hasValue,
                text = displayName(),
                onClick = {}
            )
        }
    }
}