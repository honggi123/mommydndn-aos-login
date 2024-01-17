package com.mommydndn.app.ui.care.job

import com.mommydndn.app.domain.model.CareType
import com.mommydndn.app.domain.model.PayPeriod
import com.mommydndn.app.domain.model.WorkPeriod
import java.time.DayOfWeek
import java.time.LocalTime
import java.time.ZonedDateTime

data class CareJobUiModel(
    val workPeriod: WorkPeriod,
    val careTypes: List<CareType>,
    val isClosed: Boolean,
    val title: String,
    val isLiked: Boolean,
    val neighborhoodName: String,
    val createdAt: ZonedDateTime,
    val daysOfWeek: List<DayOfWeek>,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val payPeriod: PayPeriod,
    val pay: Int,
)

val mockCareJobUiModels = buildList(capacity = 4) {
    add(
        CareJobUiModel(
            workPeriod = WorkPeriod.Regular,
            careTypes = listOf(
                CareType.SeniorCare,
            ),
            isClosed = false,
            title = "아버지 보살펴 주실 분을 구합니다",
            isLiked = true,
            neighborhoodName = "이문1동",
            createdAt = ZonedDateTime.now()
                .minusMinutes(14),
            daysOfWeek = listOf(
                DayOfWeek.MONDAY,
                DayOfWeek.WEDNESDAY,
                DayOfWeek.FRIDAY,
            ),
            startTime = LocalTime.of(17, 0),
            endTime = LocalTime.of(22, 0),
            payPeriod = PayPeriod.Hourly,
            pay = 12000,
        )
    )

    add(
        CareJobUiModel(
            workPeriod = WorkPeriod.Regular,
            careTypes = listOf(
                CareType.ChildCare,
                CareType.Housekeeping,
            ),
            isClosed = false,
            title = "2일간 풀타임으로 아이 둘 맡아주실 분 구해요",
            isLiked = false,
            neighborhoodName = "반포동",
            createdAt = ZonedDateTime.now()
                .minusHours(1),
            daysOfWeek = listOf(
                DayOfWeek.MONDAY,
                DayOfWeek.WEDNESDAY,
                DayOfWeek.FRIDAY,
            ),
            startTime = LocalTime.of(17, 0),
            endTime = LocalTime.of(22, 0),
            payPeriod = PayPeriod.Hourly,
            pay = 12000,
        )
    )

    add(
        CareJobUiModel(
            workPeriod = WorkPeriod.OneTime,
            careTypes = listOf(
                CareType.ChildCare,
            ),
            isClosed = true,
            title = "담주 화목 봐주실 분 구해요!",
            isLiked = true,
            neighborhoodName = "반포동",
            createdAt = ZonedDateTime.now()
                .minusHours(2)
                .minusMinutes(19),
            daysOfWeek = listOf(
                DayOfWeek.TUESDAY,
                DayOfWeek.THURSDAY,
            ),
            startTime = LocalTime.of(10, 0),
            endTime = LocalTime.of(19, 0),
            payPeriod = PayPeriod.Hourly,
            pay = 10000,
        )
    )

    add(
        CareJobUiModel(
            workPeriod = WorkPeriod.OneTime,
            careTypes = listOf(CareType.ChildCare),
            isClosed = false,
            title = "오늘 당장 구해요",
            isLiked = false,
            neighborhoodName = "서초동",
            createdAt = ZonedDateTime.now()
                .minusDays(2),
            daysOfWeek = listOf(DayOfWeek.MONDAY),
            startTime = LocalTime.of(10, 0),
            endTime = LocalTime.of(20, 30),
            payPeriod = PayPeriod.Hourly,
            pay = 15000,
        )
    )
}