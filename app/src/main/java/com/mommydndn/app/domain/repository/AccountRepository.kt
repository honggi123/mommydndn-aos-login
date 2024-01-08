package com.mommydndn.app.domain.repository

import com.mommydndn.app.data.network.service.google.response.GetGoogleAccessTokenResponse
import com.mommydndn.app.data.network.service.user.response.SignInResponse
import com.mommydndn.app.data.network.service.user.response.SignUpResponse
import com.mommydndn.app.domain.model.user.OAuthProvider
import com.mommydndn.app.domain.model.user.UserType

interface AccountRepository {

    suspend fun signIn(accessToken: String, OAuthProvider: OAuthProvider): SignInResponse

    suspend fun signUp(
        accessToken: String,
        oAuthType: OAuthProvider,
        userType: UserType,
        emdId: Int
    ): SignUpResponse

    suspend fun saveUserToken(
        accessToken: String,
        refreshToken: String
    ): Unit

    suspend fun getGoogleAccessToken(authCode: String): GetGoogleAccessTokenResponse
}
