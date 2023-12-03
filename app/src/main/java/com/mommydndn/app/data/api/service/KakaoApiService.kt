package com.mommydndn.app.data.api.service

import com.mommydndn.app.BuildConfig
import com.mommydndn.app.data.api.model.response.AddressResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface KakaoApiService {

    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET("v2/local/search/address.json")
    suspend fun fetchAddressInfo(
        @Header("Authorization") key: String = "KakaoAK ${BuildConfig.KAKAO_API_KEY}",
        @Query("query") query: String,
    ): ApiResponse<AddressResponse>
}
