package com.mommydndn.app.data.repository

import com.mommydndn.app.data.network.service.UserService
import com.mommydndn.app.data.network.service.request.UpdateNotificationAllowed
import com.mommydndn.app.data.network.service.request.UpdateNotificationsAllowedRequest
import com.mommydndn.app.domain.model.Notification
import com.mommydndn.app.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataRepository @Inject constructor(
    private val userService: UserService
) : UserRepository {

    override suspend fun updateNotificationsAllowed(list: List<Notification>) {
        val requset = list.map {
            UpdateNotificationAllowed(
                id = it.type.id.toInt(),
                isAllowed = it.isAllowed
            )
        }
        return userService.updateNotificationsAllowed(requset)
    }
}