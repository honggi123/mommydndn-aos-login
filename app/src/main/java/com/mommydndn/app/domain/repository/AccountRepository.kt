package com.mommydndn.app.domain.repository

import com.mommydndn.app.data.api.model.response.LoginGoogleResponse
import com.mommydndn.app.data.api.model.response.LoginResponse
import com.mommydndn.app.domain.model.user.OAuthType
import com.mommydndn.app.data.model.user.SignUpInfo
import com.mommydndn.app.data.api.model.response.SignUpResponse
import com.mommydndn.app.domain.model.user.UserType
import com.skydoves.sandwich.ApiResponse

interface AccountRepository {

    suspend fun signIn(accessToken: String, oAuthType: OAuthType): ApiResponse<LoginResponse>

    suspend fun signUp(
        accessToken: String,
        oAuthType: OAuthType,
        userType: UserType,
        emdId: Int
    ): ApiResponse<SignUpResponse>

    suspend fun getGoogleAccessToken(authCode: String): ApiResponse<LoginGoogleResponse>
}