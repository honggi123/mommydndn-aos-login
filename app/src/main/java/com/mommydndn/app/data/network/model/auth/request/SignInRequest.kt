package com.mommydndn.app.data.network.model.auth.request

import kotlinx.serialization.Serializable

@Serializable
data class SignInRequest(
    val oAuthProvider: String,
    val accessToken: String
)
