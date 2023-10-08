package com.mommydndn.app.data.api.model

import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    val accessToken: String,
    val emdId: Int,
    val oauthProvider: String,
    val userType: String
)
