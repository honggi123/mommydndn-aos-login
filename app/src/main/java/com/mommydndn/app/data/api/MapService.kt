package com.mommydndn.app.data.api

import com.mommydndn.app.data.model.NearestResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MapService {
    @GET("/api/map/nearest")
    suspend fun fetchNearestByLocation(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = 20
    ): Response<NearestResponse>

    @GET("/api/map/search")
    suspend fun fetchNearestByKeyword(
        @Query("keyword") keyword: String,
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = 20
    ): Response<NearestResponse>
}

