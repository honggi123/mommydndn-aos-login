package com.mommydndn.app.data.api.model.request

import com.mommydndn.app.data.model.care.CaringType
import com.mommydndn.app.data.model.care.CaringTypeSerializer
import com.mommydndn.app.data.model.care.SalaryType
import com.mommydndn.app.data.model.care.SalaryTypeSerializer
import com.mommydndn.app.data.model.care.WorkPeriodType
import com.mommydndn.app.data.model.care.WorkPeriodTypeSerializer
import com.mommydndn.app.data.model.common.DayOfWeekType
import com.mommydndn.app.data.model.common.DayOfWeekTypeSerializer
import com.mommydndn.app.data.model.location.EmdItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JobOfferCreationRequest(
    @SerialName("title") val title: String,
    @SerialName("content") val content: String,
    @SerialName("caringTypeCodeList")
    val caringTypeCodeList: List<@Serializable(with = CaringTypeSerializer::class) CaringType>,
    @Serializable(with = WorkPeriodTypeSerializer::class)
    @SerialName("taskTypeCode") val taskTypeCode: WorkPeriodType,
    @SerialName("dateList") val dateList: List<Long>?,
    @SerialName("days") val days: List<@Serializable(with = DayOfWeekTypeSerializer::class) DayOfWeekType>?,
    @SerialName("startDate") val startDate: Long?,
    @SerialName("endDate") val endDate: Long?,
    @SerialName("startTime") val startTime: String?,
    @SerialName("endTime") val endTime: String?,
    @SerialName("emd") val emd: EmdItem,
    @SerialName("latitude") val latitude: Double,
    @SerialName("longitude") val longitude: Double,
    @Serializable(with = SalaryTypeSerializer::class)
    @SerialName("salaryTypeCode") val salaryTypeCode: SalaryType,
    @SerialName("salary") val salary: Int?,
    @SerialName("indOtherConditionIdList") val indOtherConditionIdList: List<Int>,
    @SerialName("imageIdList") val imageIdList: List<Int>
)