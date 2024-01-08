package com.mommydndn.app.data.network.feature.user.response

import com.mommydndn.app.data.model.notification.Notification
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

typealias GetNotificationSettingListResponse = List<NotificationSettingApiModel>

@Serializable
data class NotificationSettingApiModel(
    @SerialName("noticeTypeId")
    val id: Int,
    val isApproved: Boolean,
    val noticeTypeName: String
)

fun GetNotificationSettingListResponse.toDomain(): List<Notification> {
    return this.map {
        Notification(
            isApproved = it.isApproved,
            noticeTypeId = it.id,
            noticeTypeName = it.noticeTypeName,
            isSelected = false
        )
    }
}