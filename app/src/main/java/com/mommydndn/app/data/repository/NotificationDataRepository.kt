package com.mommydndn.app.data.repository

import com.mommydndn.app.data.mapper.toDomain
import com.mommydndn.app.data.network.service.CommonService
import com.mommydndn.app.data.network.service.NotificationService
import com.mommydndn.app.domain.model.Notification
import com.mommydndn.app.domain.repository.NotificationRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationDataRepository @Inject constructor(
    private val notificationService: NotificationService
) : NotificationRepository {

    override suspend fun getNotifications(): List<Notification> {
       return notificationService.getNotificationSettings().toDomain()
    }

}

