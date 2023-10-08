package com.mommydndn.app.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Banner(
    val bannerId: Int,
    val url: String,
    val targetUrl: String
)