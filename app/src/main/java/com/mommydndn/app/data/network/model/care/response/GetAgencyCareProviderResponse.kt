package com.mommydndn.app.data.network.model.care.response


import com.mommydndn.app.data.network.model.care.AgencyOtherConditionApiModel
import com.mommydndn.app.data.network.model.care.CareTypeApiModel
import com.mommydndn.app.data.network.model.common.LocationApiModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetAgencyCareProviderResponse(
    @SerialName("companyId")
    val id: Int,
    @SerialName("companyAuthor")
    val writer: AgencyCareProviderWriterApiModel,
    @SerialName("coverImageList")
    val coverImages: List<AgencyCareProviderCoverImageApiModel>,
    @SerialName("emd")
    val neighborhood: LocationApiModel,
    @SerialName("introLine")
    val introduction: String,
    @SerialName("maxMonthlySalary")
    val maxSalary: Int,
    @SerialName("minMonthlySalary")
    val minSalary: Int,
    @SerialName("caringTypeCodeList")
    val careTypes: List<CareTypeApiModel>,
    @SerialName("comOtherConditionList")
    val otherConditions: List<AgencyOtherConditionApiModel>,
    val isLiked: Boolean,
    val commission: Int,
)

@Serializable
data class AgencyCareProviderCoverImageApiModel(
    @SerialName("imageId")
    val id: Int,
    val url: String
)

@Serializable
data class AgencyCareProviderWriterApiModel(
    @SerialName("userId")
    val id: Int,
    @SerialName("reviewList")
    val reviews: List<Review>,
    val createdAt: Int,
    val dndnScore: Int,
    val isDnDnAuthenticated: Boolean,
    val matchingCount: Int,
    val nickname: String,
    val profileUrl: String,
    val responseRate: String,
    val reviewCount: Int
)