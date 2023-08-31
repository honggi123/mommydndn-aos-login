package com.mommydndn.app.data.respository.impl

import android.util.Log
import com.mommydndn.app.data.Preferences
import com.mommydndn.app.data.api.ApiService
import com.mommydndn.app.data.model.LoginRequest
import com.mommydndn.app.data.model.LoginType
import com.mommydndn.app.data.respository.AccountRepository
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val preferences: Preferences
) : AccountRepository {
    override suspend fun signIn(tokenId: String, type: LoginType) {
        val request = LoginRequest(tokenId)
        val response = when (type) {
            LoginType.GOOGLE -> apiService.loginGoogle(request)
            LoginType.KAKAO -> apiService.loginKakao(request)
            LoginType.NAVER -> apiService.loginNaver(request)
        }

        if (response.isSuccessful) {
            val loginResponse = response.body()
            preferences.putAccessToken(loginResponse?.accessToken)
            preferences.putRefreshToken(loginResponse?.refreshToken)
        }
    }
}