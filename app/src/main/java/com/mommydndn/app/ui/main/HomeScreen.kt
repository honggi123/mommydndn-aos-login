package com.mommydndn.app.ui.main

import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mommydndn.app.data.model.Banner
import com.mommydndn.app.data.model.CommunityPost
import com.mommydndn.app.data.model.MarketListItem
import com.mommydndn.app.ui.components.modal.NoticeSettingListModal
import com.mommydndn.app.ui.components.box.SubtextBox
import com.mommydndn.app.ui.components.box.SubtextBoxSize
import com.mommydndn.app.ui.components.common.BannerList
import com.mommydndn.app.ui.components.box.CommunityPostBox
import com.mommydndn.app.ui.components.common.Footer
import com.mommydndn.app.ui.components.box.JobOfferBox
import com.mommydndn.app.ui.components.box.MarketListItemBox
import com.mommydndn.app.ui.components.box.ProfileSitterBox
import com.mommydndn.app.ui.components.common.SubBanner
import com.mommydndn.app.ui.theme.Grey50
import com.mommydndn.app.ui.theme.GreyOpacity400
import com.mommydndn.app.ui.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainHomeScreen(
    navHostController: NavHostController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val noticeSettings by viewModel.noticeSettings.collectAsState()
    val banners by viewModel.banners.collectAsState()
    val jobSeekers by viewModel.jobSeekers.collectAsState()
    val jobOffers by viewModel.jobOffers.collectAsState()
    val babyItems by viewModel.babyItems.collectAsState()

    val ex3: List<CommunityPost> = listOf(
        CommunityPost(
            userProfileImgUrl = "https://example.com/user1.jpg",
            userName = "User 1",
            date = "2023-09-15",
            title = "첫 번째 게시물",
            content = "이것은 첫 번째 게시물의 내용입니다."
        ),
        CommunityPost(
            userProfileImgUrl = "https://example.com/user2.jpg",
            userName = "User 2",
            date = "2023-09-14",
            title = "두 번째 게시물",
            content = "이것은 두 번째 게시물의 내용입니다."
        ),
        CommunityPost(
            userProfileImgUrl = "https://example.com/user2.jpg",
            userName = "User 3",
            date = "2023-09-14",
            title = "세 번째 게시물",
            content = "이것은 세 번째 게시물의 내용입니다."
        ),
    )

    val ex4: List<List<MarketListItem>> = listOf(
        MarketListItem(
            productImgUrl = "https://example.com/product1.jpg",
            price = "10,000원",
            productName = "상품 1",
            region = "서울",
            time = "2시간 전"
        ),
        MarketListItem(
            productImgUrl = "https://example.com/product2.jpg",
            price = "20,000원",
            productName = "상품 2",
            region = "부산",
            time = "1시간 전"
        ),
        MarketListItem(
            productImgUrl = "https://example.com/product2.jpg",
            price = "20,000원",
            productName = "상품 2",
            region = "부산",
            time = "1시간 전"
        ),
        MarketListItem(
            productImgUrl = "https://example.com/product2.jpg",
            price = "20,000원",
            productName = "상품 2",
            region = "부산",
            time = "1시간 전"
        ),
        MarketListItem(
            productImgUrl = "https://example.com/product2.jpg",
            price = "20,000원",
            productName = "상품 2",
            region = "부산",
            time = "1시간 전"
        ),
        MarketListItem(
            productImgUrl = "https://example.com/product2.jpg",
            price = "20,000원",
            productName = "상품 2",
            region = "부산",
            time = "1시간 전"
        ),
        MarketListItem(
            productImgUrl = "https://example.com/product2.jpg",
            price = "20,000원",
            productName = "상품 2",
            region = "부산",
            time = "1시간 전"
        ),
    ).chunked(2)

    LazyColumn {
        item {
            BannerList(items = banners)
        }
        item {
            SubtextBox(size = SubtextBoxSize.L, titleText = "가장 가까운 시터님", rightText = "전체보기")
            LazyRow(
                modifier = Modifier.padding(start = 32.dp, top = 28.dp, bottom = 36.dp),
                horizontalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                items(jobSeekers) { item ->
                    ProfileSitterBox(item = item)
                }
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Grey50)
                    .padding(20.dp)
            )
        }
        item {
            SubtextBox(size = SubtextBoxSize.L, titleText = "도움이 필요한 주변 이웃", rightText = "더보기")
            LazyRow(
                modifier = Modifier.padding(start = 32.dp, top = 28.dp, bottom = 36.dp),
                horizontalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                items(jobOffers) { item ->
                    JobOfferBox(item = item)
                }
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Grey50)
                    .padding(20.dp)
            )
        }
        item {
            SubtextBox(size = SubtextBoxSize.L, titleText = "자유수다 추천글")
            Column(
                modifier = Modifier.padding(start = 32.dp, top = 28.dp, bottom = 36.dp),
            ) {
                ex3.forEach { item ->
                    CommunityPostBox(item = item)
                }
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Grey50)
                    .padding(20.dp)
            )
        }
        item {
            SubtextBox(size = SubtextBoxSize.L, titleText = "집 앞 육아용품 장터")
            Column(
                modifier = Modifier.padding(
                    start = 24.dp,
                    top = 28.dp,
                    bottom = 24.dp,
                    end = 28.dp
                ),
            ) {
                babyItems.chunked(2).forEach { rowItems ->
                    Row {
                        rowItems.forEach { item ->
                            MarketListItemBox(item = item)
                            Spacer(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(12.dp)
                            )
                        }
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.padding(20.dp))
        }
        item {
            SubBanner()
            Footer() {}
        }
    }

    val sheetState =
        rememberModalBottomSheetState(
            if (noticeSettings.isEmpty()) ModalBottomSheetValue.Hidden else ModalBottomSheetValue.Expanded,
            skipHalfExpanded = true,
            animationSpec = spring(
                dampingRatio = 0.85f,
                stiffness = 100f
            )
        )
    val scope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContentColor = Color.Transparent,
        sheetBackgroundColor = Color.Transparent,
        scrimColor = GreyOpacity400,
        sheetElevation = 0.dp,
        sheetContent = {
            NoticeSettingListModal(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 32.dp),
                onDismiss = {
                    scope.launch { sheetState.hide() }
                },
                onItemSelected = { index, isChecked ->

                },
                onComplete = { },
                itemList = noticeSettings,
                titleCheckBoxText = "꼭 필요한 알림만 보내드릴게요"
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