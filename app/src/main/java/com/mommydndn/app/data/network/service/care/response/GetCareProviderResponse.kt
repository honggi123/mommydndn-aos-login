package com.mommydndn.app.data.network.service.care.response


import com.mommydndn.app.data.network.service.care.model.CareTypeApiModel
import com.mommydndn.app.data.network.service.care.model.IndividualOtherConditionApiModel
import com.mommydndn.app.data.network.service.care.model.SalaryTypeApiModel
import com.mommydndn.app.data.network.service.common.model.LocationApiModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetCareProviderResponse(
    @SerialName("jobSeekerId")
    val id: Int,
    @SerialName("emd")
    val neighborhood: LocationApiModel,
    @SerialName("introLine")
    val introduction: String,
    @SerialName("jobSeekerAuthor")
    val writer: CareProviderWriter,
    @SerialName("salaryTypeCode")
    val salaryType: SalaryTypeApiModel,
    @SerialName("caringTypeCodeList")
    val careTypes: List<CareTypeApiModel>,
    @SerialName("indOtherConditionList")
    val otherConditionList: List<IndividualOtherConditionApiModel>,
    val latitude: Int,
    val longitude: Int,
    val salary: Int,
    val isLiked: Boolean
)

@Serializable
data class CareProviderWriter(
    @SerialName("userId")
    val userId: Int,
    @SerialName("reviewList")
    val reviews: List<CareProviderReviewApiModel>,
    @SerialName("certificationList")
    val certifications: List<CareProviderCertificationApiModel>,
    val matchingCount: Int,
    val nickname: String,
    val profileUrl: String,
    val responseRate: String,
    val reviewCount: Int,
    val createdAt: Int,
    val dndnScore: Int,
    val isDnDnAuthenticated: Boolean,
)

@Serializable
data class CareProviderReviewApiModel(
    @SerialName("caringReviewId")
    val id: Int,
    @SerialName("caringTypeCodeList")
    val careTypes: List<String>,
    val content: String,
    val nickname: String,
    val rate: Int,
    val createdAt: String
)

@Serializable
data class CareProviderCertificationApiModel(
    @SerialName("userCertificationId")
    val id: Int,
    @SerialName("certificationName")
    val name: String,
    @SerialName("certificationTypeCode")
    val certificationType: String,
    val updatedAt: Int
)