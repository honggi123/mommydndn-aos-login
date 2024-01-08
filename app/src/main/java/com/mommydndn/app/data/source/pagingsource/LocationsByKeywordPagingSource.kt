package com.mommydndn.app.data.source.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mommydndn.app.data.network.service.common.model.LocationApiModel
import com.mommydndn.app.data.network.service.location.LocationService
import com.mommydndn.app.domain.model.location.Neighborhood
import javax.inject.Inject

private const val STARTING_PAGE_INDEX = 1
class LocationsByKeywordPagingSource @Inject constructor(
    private val keyWord: String,
    private val locationService: LocationService
) : PagingSource<Int, LocationApiModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LocationApiModel> {
        return try {
            val position = params.key ?: STARTING_PAGE_INDEX
            val result =
                locationService.fetchLocationsByKeyword(
                    keyWord,
                    skip = (position - 1) * params.loadSize,
                    limit = params.loadSize,
                    requestTimestamp = System.currentTimeMillis()
                )
            val data = result.items.map { it } ?: emptyList()

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
    override fun getRefreshKey(state: PagingState<Int, LocationApiModel>): Int? {
        return state.anchorPosition
    }
}