package com.mommydndn.app.data.network.service.care.response


import com.mommydndn.app.data.network.model.NetworkCareType
import com.mommydndn.app.data.network.model.NetworkNeighborhood
import com.mommydndn.app.data.network.service.care.model.NetworkWorkerOtherCondition
import com.mommydndn.app.data.network.service.care.model.SalaryTypeApiModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetCareWorkerResponse(
    @SerialName("jobSeekerId")
    val id: Int,
    @SerialName("emd")
    val location: NetworkNeighborhood,
    @SerialName("introLine")
    val introduction: String,
    @SerialName("jobSeekerAuthor")
    val writer: CareWorkerWriter,
    @SerialName("salaryTypeCode")
    val salaryType: SalaryTypeApiModel,
    @SerialName("caringTypeCodeList")
    val careTypes: List<NetworkCareType>,
    @SerialName("indOtherConditionList")
    val otherConditionList: List<NetworkWorkerOtherCondition>,
    val latitude: Int,
    val longitude: Int,
    val salary: Int,
    val isLiked: Boolean
)

@Serializable
data class CareWorkerWriter(
    @SerialName("userId")
    val userId: Int,
    @SerialName("reviewList")
    val reviews: List<CareWorkerReviewApiModel>,
    @SerialName("certificationList")
    val certifications: List<CareWorkerCertificationApiModel>,
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
data class CareWorkerReviewApiModel(
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
data class CareWorkerCertificationApiModel(
    @SerialName("userCertificationId")
    val id: Int,
    @SerialName("certificationName")
    val name: String,
    @SerialName("certificationTypeCode")
    val certificationType: String,
    val updatedAt: Int
)