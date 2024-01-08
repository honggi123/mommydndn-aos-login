package com.mommydndn.app.data.source.pagingsource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mommydndn.app.data.network.service.care.CareService
import com.mommydndn.app.data.network.service.care.request.GetJobOpeningListRequest
import com.mommydndn.app.data.network.service.care.response.JobOpeningSummaryApiModel
import com.mommydndn.app.data.network.service.common.model.PaginationApiModel
import javax.inject.Inject

private const val STARTING_PAGE_INDEX = 1

class JobOpeningSummaryPagingSource @Inject constructor(
    private val getJobOpeningListRequest: GetJobOpeningListRequest,
    private val careService: CareService
) : PagingSource<Int, JobOpeningSummaryApiModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, JobOpeningSummaryApiModel> {
        return try {
            val position = params.key ?: STARTING_PAGE_INDEX
            val result = careService.fetchJobOpeningSummaryList(
                getJobOpeningListRequest.map {
                    it.copy(
                        pageMeta = PaginationApiModel(
                            page = position,
                            size = 5,
                            requestedAt = System.currentTimeMillis()
                        )
                    )
                }
            )

            val data = result?.items ?: emptyList()

            LoadResult.Page(
                data = data,
                prevKey = when (position) {
                    STARTING_PAGE_INDEX -> null
                    else -> position - 1
                },
                nextKey = if (data.isEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            Log.e("exception", e.message.toString())
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, JobOpeningSummaryApiModel>): Int? {
        return state.anchorPosition
    }
}