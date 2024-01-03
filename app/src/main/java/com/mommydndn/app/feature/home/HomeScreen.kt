package com.mommydndn.app.feature.home

import android.annotation.SuppressLint
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mommydndn.app.R
import com.mommydndn.app.data.model.notification.Notification
import com.mommydndn.app.domain.model.care.JobOffer
import com.mommydndn.app.domain.model.care.JobSeeker
import com.mommydndn.app.ui.deprecated.components.box.FooterBox
import com.mommydndn.app.ui.deprecated.components.box.SubtextBox
import com.mommydndn.app.ui.deprecated.components.box.SubtextBoxSize
import com.mommydndn.app.ui.deprecated.components.common.Header
import com.mommydndn.app.ui.deprecated.components.common.SubBanner
import com.mommydndn.app.ui.deprecated.components.list.BannerList
import com.mommydndn.app.ui.deprecated.components.modal.NoticeSettingListModal
import com.mommydndn.app.ui.theme.Grey50
import com.mommydndn.app.ui.theme.GreyOpacity400
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun HomeRoute(
    onMoreJobOfferButtonClick: () -> Unit,
    viewModel: HomeViewModel = viewModel() // hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val scope = rememberCoroutineScope()

    when (val uiState = uiState) {
        is HomeUiState.Loading -> {
            // TODO
        }

        is HomeUiState.Success -> {

            HomeScreen(
                modifier = Modifier.fillMaxSize(),
                uiState = uiState,
                onMoreJobOfferButtonClick = onMoreJobOfferButtonClick,
            )

            BottomSheetModal(
                scope = scope,
                noticeSettings = uiState.notifications
            )
        }

        is HomeUiState.Failure -> {
            // TODO
        }
    }


}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onMoreJobOfferButtonClick: () -> Unit,
    uiState: HomeUiState.Success,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
    ) {
        HomeTopAppBar(
            modifier = Modifier.fillMaxWidth()
        )

        Column(
            modifier = Modifier.verticalScroll(scrollState)
        ) {
            BannerList(
                modifier = Modifier.fillMaxWidth(),
                items = uiState.banners
            )

            JobSeekerContent(
                modifier = Modifier.fillMaxWidth(),
                jobSeekers = uiState.jobSeekers
            )

            HomeDivider(modifier = Modifier.fillMaxWidth())

            JobOfferContent(
                modifier = Modifier.fillMaxWidth(),
                jobOffers = uiState.jobOffers,
                onMoreButtonClick = { onMoreJobOfferButtonClick() }
            )

            HomeDivider(modifier = Modifier.fillMaxWidth())

//            BabyItemsContent(
//                modifier = Modifier.fillMaxWidth(),
//                babyItemUiState = itemsUiState,
//                loadNextPage = { loadNextBabyItemPage(it) }
//            )
//
//            HomeDivider(modifier = Modifier.fillMaxWidth())

            SubBanner(modifier = Modifier.fillMaxWidth())

            FooterBox(
                modifier = Modifier.fillMaxWidth(),
                onInquiryClick = {}
            )
        }
    }
}

@Composable
fun HomeTopAppBar(
    modifier: Modifier = Modifier
) {
    Header(leftContent = {
        Image(
            painter = painterResource(id = R.drawable.icon_logo),
            contentDescription = "icon_logo",
            modifier = modifier
                .size(36.dp)
        )
    }, rightContent = {
        Image(
            painter = painterResource(id = R.drawable.icon_headset),
            contentDescription = "icon_headset",
            modifier = Modifier
                .size(36.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.icon_bell),
            contentDescription = "icon_bell",
            modifier = Modifier
                .size(36.dp)
        )
    }
    )
}

@Composable
fun JobSeekerContent(
    jobSeekers: List<JobSeeker>,
    modifier: Modifier = Modifier
) {
    if (jobSeekers.isEmpty()) {
        return
    }
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        SubtextBox(
            modifier = Modifier.fillMaxWidth(),
            size = SubtextBoxSize.L,
            title = stringResource(id = R.string.category_job_seekers_title),
            subtitle = "",
            trailingLabel = stringResource(id = R.string.see_all)
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, top = 28.dp, bottom = 36.dp),
            horizontalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            items(jobSeekers) { item ->
                // TODO
                // CareProviderProfile(item)
            }
        }
    }
}

@Composable
fun JobOfferContent(
    onMoreButtonClick: () -> Unit,
    jobOffers: List<JobOffer> = emptyList(),
    modifier: Modifier = Modifier,
) {
    if (jobOffers.isEmpty()) {
        return
    }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        SubtextBox(
            modifier = Modifier.fillMaxWidth(),
            size = SubtextBoxSize.L,
            title = stringResource(id = R.string.category_job_offers_title),
            subtitle = "",
            trailingLabel = stringResource(id = R.string.see_more),
            onClick = { onMoreButtonClick() }
        )

        Box(modifier = Modifier.padding(start = 32.dp, top = 28.dp, bottom = 36.dp)) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                /*
items(jobOffers) { item ->
                    JobOfferBox(
                        modifier = Modifier.width(216.dp),
                        item = item
                    )
                }
                 */
            }
        }
    }

}


@Composable
fun HomeDivider(
    modifier: Modifier = Modifier
) {
    Divider(
        color = Grey50,
        modifier = modifier
            .fillMaxWidth()
            .height(20.dp)
    )
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetModal(
    scope: CoroutineScope,
    noticeSettings: List<Notification>
) {

    val sheetState = rememberModalBottomSheetState(
        ModalBottomSheetValue.Expanded,
        skipHalfExpanded = true,
        animationSpec = spring(
            dampingRatio = 0.85f,
            stiffness = 100f
        )
    )

    if (noticeSettings.isNotEmpty()) {
        ModalBottomSheetLayout(
            modifier = Modifier,
            sheetState = sheetState,
            sheetContentColor = Color.Transparent,
            sheetBackgroundColor = Color.Transparent,
            scrimColor = GreyOpacity400,
            sheetElevation = 0.dp,
            sheetContent = {
                NoticeSettingListModal(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 100.dp),
                    onDismiss = { scope.launch { sheetState.hide() } },
                    onItemSelected = { index, isChecked ->
                        // TODO
                    },
                    onComplete = {
                        // TODO
                    },
                    itemList = noticeSettings,
                    titleCheckBoxText = stringResource(id = R.string.send_necessary_notifications)
                )
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
            )
        }
    }
}


//@Composable
//fun BabyItemsContent(
//    babyItemUiState: HomeBabyItemUiState,
//    loadNextPage: (Int) -> Unit,
//    modifier: Modifier = Modifier,
//) {
//    val babyItems = babyItemUiState.babyItems
//
//    if (babyItems.isEmpty()) {
//        return
//    }
//
//    Column(
//        modifier = modifier.fillMaxWidth()
//    ) {
//
//        SubtextBox(
//            modifier = Modifier.fillMaxWidth(),
//            size = SubtextBoxSize.L,
//            titleText = stringResource(id = R.string.category_baby_items_title)
//        )
//
//        Box(modifier = modifier.fillMaxWidth()) {
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(
//                        horizontal = 24.dp, vertical = 28.dp
//                    ),
//            ) {
//                babyItems.chunked(2).forEach { rowItems ->
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.spacedBy(12.dp)
//                    ) {
//                        rowItems.forEach { item ->
//                            MarketListItemBox(modifier = Modifier.weight(1f), item = item)
//                        }
//                    }
//
//                    Spacer(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(24.dp)
//                    )
//                }
//            }
//        }
//
//        when (babyItemUiState) {
//            is HomeBabyItemUiState.Success -> {
//
//                // 1.현재 페이지 <= MAX_MORE_BABY_ITEM_PAGE
//                // 2.현재까지 아이템 총 개수에 추가 되어야하는 아이템 개수를 더했을 때 보다 다음 페이지까지의 총 개수가 더 적을 경우
//                //  1 page -> 0 + 추가 되어야하는 아이템 개수 < 아이템의 총 개수
//                //  2 page -> (1 * 추가 되어야하는 아이템 개수) + 추가 되어야하는 아이템 개수 < 아이템의 총 개수
//
//                val shouldShowLoadMoreButton =
//                    babyItemUiState.babyItemsPagingMeta.currentPageNum <= MAX_BABY_ITEM_PAGES
//                            && ((babyItemUiState.babyItemsPagingMeta.currentPageNum - 1) * MORE_BABY_ITEM_SIZE) + MORE_BABY_ITEM_SIZE < babyItemUiState.babyItemsPagingMeta.totalCount
//
//                if (shouldShowLoadMoreButton) {
//                    Button(
//                        modifier = Modifier
//                            .border(width = 1.dp, color = Color(0xFFF0F2F4))
//                            .fillMaxWidth(),
//                        onClick = {
//                            loadNextPage(babyItemUiState.babyItemsPagingMeta.currentPageNum)
//                        }
//                    ) {
//                        Text(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(top = 20.dp, bottom = 20.dp),
//                            text = stringResource(id = R.string.see_more),
//                            style = MaterialTheme.typography.paragraph300.copy(
//                                fontWeight = FontWeight.Normal,
//                                color = Salmon600
//                            ),
//                            textAlign = TextAlign.Center
//                        )
//                    }
//                }
//            }
//
//            is HomeBabyItemUiState.Loading -> {
//                // TODO
//            }
//        }
//
//    }
//}