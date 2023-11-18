package com.mommydndn.app.data.datasource.pagingsource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mommydndn.app.data.api.model.request.CompanyListRequest
import com.mommydndn.app.data.api.model.request.JobOfferListRequest
import com.mommydndn.app.data.api.model.request.PaginationRequest
import com.mommydndn.app.data.api.service.CaringService
import com.mommydndn.app.data.model.care.summary.CompanySummaryListItem
import com.mommydndn.app.data.model.care.summary.JobOfferSummaryListItem
import javax.inject.Inject

private const val STARTING_PAGE_INDEX = 1
class CompanySummaryPagingSource @Inject constructor(
    private val companyListRequest: CompanyListRequest,
    private val caringService: CaringService
) : PagingSource<Int, CompanySummaryListItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CompanySummaryListItem> {
        return try {
            val position = params.key ?: STARTING_PAGE_INDEX
            val result = caringService.fetchCompanySummary(
                companyListRequest.copy(
                    paginationRequest = PaginationRequest(
                        pageNum = position,
                        pageSize = 5,
                        requestTimestamp = System.currentTimeMillis()
                    )
                )
            )

            Log.e("result",result.errorBody().toString())

            val data = result.body()?.jobOfferSummaryList ?: emptyList()

            LoadResult.Page(
                data = data,
                prevKey = when (position) {
                    STARTING_PAGE_INDEX -> null
                    else -> position - 1
                },
                nextKey = if (data.isEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CompanySummaryListItem>): Int? {
        return state.anchorPosition
    }
}